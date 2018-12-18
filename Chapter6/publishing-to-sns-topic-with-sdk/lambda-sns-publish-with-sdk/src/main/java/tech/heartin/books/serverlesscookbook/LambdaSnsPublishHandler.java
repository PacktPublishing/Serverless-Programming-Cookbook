package tech.heartin.books.serverlesscookbook;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

import tech.heartin.books.serverlesscookbook.domain.Request;

/**
 * RequestHandler implementation.
 */
public final class LambdaSnsPublishHandler implements RequestHandler<Request, String> {

    private final AmazonSNS snsClient;

    public LambdaSnsPublishHandler() {
        this.snsClient = AmazonSNSClientBuilder.standard()
                .withRegion(System.getenv("AWS_REGION"))
                .build();
    }

    /**
     * Handle request.
     *
     * @param request  - input to lambda handler.
     * @param context - context object.
     * @return Message id of the published message.
     */
    public String handleRequest(final Request request, final Context context) {
        context.getLogger().log("Received Request: " + request);

        final PublishResult result;
        try {
            PublishRequest publishRequest = new PublishRequest(request.getTopicArn(), request.getMessage());
            result = snsClient.publish(publishRequest);
        } catch (Exception e) {
            return "Exception occurred: " + e.getMessage();
        }

        return "Message Id: " + result.getMessageId();
    }
}
