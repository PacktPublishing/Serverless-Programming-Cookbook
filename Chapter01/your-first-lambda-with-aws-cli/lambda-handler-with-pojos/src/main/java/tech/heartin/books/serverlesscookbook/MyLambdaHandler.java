package tech.heartin.books.serverlesscookbook;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import tech.heartin.books.serverlesscookbook.domain.HandlerRequest;
import tech.heartin.books.serverlesscookbook.domain.HandlerResponse;

/**
 * RequestHandler implementation.
 */
public final class MyLambdaHandler implements RequestHandler<HandlerRequest, HandlerResponse> {

    /**
     * Handle request.
     *
     * @param request  - input to lambda handler
     * @param context - context object
     * @return greeting text
     */
    public HandlerResponse handleRequest(final HandlerRequest request,
                                         final Context context) {
        context.getLogger().log("Hello " + request.getName());

        return new HandlerResponse("Hello " + request.getName());
    }
}
