package tech.heartin.books.serverlesscookbook;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import tech.heartin.books.serverlesscookbook.services.SnsService;
import tech.heartin.books.serverlesscookbook.services.SnsServiceImpl;

/**
 * RequestHandler implementation.
 */
public final class LambdaSnsEventHandler implements RequestHandler<SNSEvent, Boolean> {

    private final AmazonSQS sqsClient;

    public LambdaSnsEventHandler() {
        this.sqsClient = AmazonSQSClientBuilder.standard()
                .withRegion(System.getenv("AWS_REGION"))
                .build();
    }

    /**
     * Handle request.
     *
     * @param snsEvent  - SQS Event passed as input to lambda handler
     * @param context - context object
     * @return true if success, else false.
     */
    public Boolean handleRequest(final SNSEvent snsEvent, final Context context) {
        context.getLogger().log("Received SQS event: " + snsEvent);

        final SnsService snsService =  new SnsServiceImpl(this.sqsClient);
        // It is a good practice to prefix environment variables with a project specific prefix.
        // E.g. SPC is a prefix that denote Serverless Programming Cookbook.
        return snsService.processEvent(snsEvent, System.getenv("SPC_OUTPUT_QUEUE_URL"), context.getLogger());

    }
}
