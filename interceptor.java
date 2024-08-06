import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.interceptor.AbstractSoapFaultDefinitionExceptionResolver;

public class ValidationResponseInterceptor implements EndpointInterceptor {

    @Override
    public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
        // Handle request (before endpoint invocation)
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception {
        // Modify the response after the endpoint has been invoked
        ValidationResponse response = (ValidationResponse) messageContext.getResponse();
        if (response != null) {
            response.setMessage("Modified in EndpointInterceptor");
        }
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext, Object endpoint) throws Exception {
        // Handle faults
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) throws Exception {
        // Cleanup after completion
    }
}