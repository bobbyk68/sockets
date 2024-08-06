import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Main {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface TrimErrors {
        int maxSize() default 9999;
    }

    public static class ValidationResponse {
        private List<String> errors = new ArrayList<>();

        public List<String> getErrors() {
            return errors;
        }

        public void setErrors(List<String> errors) {
            this.errors = errors;
        }

        public void addError(String error) {
            this.errors.add(error);
        }

        @TrimErrors
        public void validate() {
            // Validation logic
        }
    }

    public static class TrimErrorsDecorator {

        public static void applyTrimIfNecessary(Object obj) {
            try {
                Method[] methods = obj.getClass().getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(TrimErrors.class)) {
                        TrimErrors trimErrors = method.getAnnotation(TrimErrors.class);
                        int maxSize = trimErrors.maxSize();

                        // Assuming the method is a validation method that populates errors
                        method.invoke(obj);

                        // Reflection to access the errors field
                        Method getErrorsMethod = obj.getClass().getMethod("getErrors");
                        List<String> errors = (List<String>) getErrorsMethod.invoke(obj);

                        if (errors.size() > maxSize) {
                            errors = errors.subList(0, maxSize);
                            Method setErrorsMethod = obj.getClass().getMethod("setErrors", List.class);
                            setErrorsMethod.invoke(obj, errors);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ValidationResponse validationResponse = new ValidationResponse();
        
        // Add more than 9999 errors for testing
        for (int i = 0; i < 10000; i++) {
            validationResponse.addError("Error " + i);
        }
        
        // Apply the decorator to trim the errors if necessary
        TrimErrorsDecorator.applyTrimIfNecessary(validationResponse);
        
        // Output the size of errors to verify
        System.out.println("Number of errors: " + validationResponse.getErrors().size());
    }
}