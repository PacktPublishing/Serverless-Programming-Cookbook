import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public final class MyLambdaHandler implements RequestHandler<HandlerRequest, HandlerResponse> {
    public HandlerResponse handleRequest(final HandlerRequest request,
                                         final Context context) {
        context.getLogger().log("Hello " + request.getName());
        return new HandlerResponse("Hello " + request.getName());
    }
}