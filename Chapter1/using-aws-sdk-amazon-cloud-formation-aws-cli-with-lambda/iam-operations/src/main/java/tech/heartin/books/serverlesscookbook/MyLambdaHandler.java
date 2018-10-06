package tech.heartin.books.serverlesscookbook;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import tech.heartin.books.serverlesscookbook.domain.IAMOperationRequest;
import tech.heartin.books.serverlesscookbook.domain.IAMOperationResponse;
import tech.heartin.books.serverlesscookbook.services.IAMService;

/**
 * RequestHandler implementation.
 */
public final class MyLambdaHandler implements RequestHandler<IAMOperationRequest, IAMOperationResponse> {

    private IAMService service;

    public MyLambdaHandler() {
        service = new IAMService();
    }


    /**
     * Handle request.
     *
     * @param request  - input to lambda handler
     * @param context - context object
     * @return greeting text
     */
    public IAMOperationResponse handleRequest(final IAMOperationRequest request, final Context context) {
        context.getLogger().log("Requested operation = " + request.getOperation()
                + ". User name = " + request.getUserName());

        switch (request.getOperation()) {
            case "CREATE" :
                return this.service.createUser(request.getUserName());
            case "CHECK" :
                return  this.service.checkUser(request.getUserName());
            case "DELETE" :
                return this.service.deleteUser(request.getUserName());

                default:
                    return new IAMOperationResponse(null,
                            "Invalid operation " + request.getOperation()
                                    + ". Allowed: CREATE, CHECK, DELETE.");

        }
    }
}
