import java.util.ArrayList;
import java.util.List;

// Assuming ValidationResponse is the generated class
class ValidationResponse {
    private List<String> errors = new ArrayList<>();

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}

interface ValidationResponseAdapter {
    List<String> getErrors();
}

class ValidationResponseAdapterImpl implements ValidationResponseAdapter {
    private final ValidationResponse validationResponse;

    public ValidationResponseAdapterImpl(ValidationResponse validationResponse) {
        this.validationResponse = validationResponse;
    }

    @Override
    public List<String> getErrors() {
        return validationResponse.getErrors();
    }

    public void setErrors(List<String> errors) {
        validationResponse.setErrors(errors);
    }
}

class TrimErrorsDecorator extends ValidationResponseAdapterImpl {
    private static final int MAX_ERRORS = 9999;

    public TrimErrorsDecorator(ValidationResponse validationResponse) {
        super(validationResponse);
    }

    public void trimErrors() {
        List<String> errors = getErrors();
        if (errors.size() > MAX_ERRORS) {
            List<String> trimmedErrors = errors.subList(0, MAX_ERRORS);
            setErrors(trimmedErrors);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Create an instance of the generated ValidationResponse class
        ValidationResponse validationResponse = new ValidationResponse();
        
        // Adapter for the generated ValidationResponse
        ValidationResponseAdapterImpl adapter = new ValidationResponseAdapterImpl(validationResponse);

        // Add more than 9999 errors for testing
        for (int i = 0; i < 10000; i++) {
            adapter.getErrors().add("Error " + i);
        }

        // Wrap the ValidationResponse with the TrimErrorsDecorator
        TrimErrorsDecorator decoratedResponse = new TrimErrorsDecorator(validationResponse);

        // Call the trimErrors method to trim the errors if necessary
        decoratedResponse.trimErrors();

        // Output the size of errors to verify
        System.out.println("Number of errors: " + decoratedResponse.getErrors().size());  // Should output 9999
    }
}