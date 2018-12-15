package tech.heartin.books.serverlesscookbook;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import tech.heartin.books.serverlesscookbook.domain.Request;
import tech.heartin.books.serverlesscookbook.domain.Response;

/**
 * RequestHandler implementation.
 */
public final class LambdaKinesisSdkWriteHandler implements RequestHandler<Request, Response> {

    /**
     * Handle request.
     *
     * @param request  - input to lambda handler
     * @param context - context object
     * @return greeting text
     */
    public Response handleRequest(final Request request,
                                  final Context context) {
        context.getLogger().log("Hello " + request.getStreamName());

        return new Response("Hello " + request.getStreamName());
    }
}
