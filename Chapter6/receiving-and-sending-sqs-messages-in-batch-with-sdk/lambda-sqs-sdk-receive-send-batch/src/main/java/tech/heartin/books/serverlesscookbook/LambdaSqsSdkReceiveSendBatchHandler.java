package tech.heartin.books.serverlesscookbook;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import tech.heartin.books.serverlesscookbook.domain.Request;
import tech.heartin.books.serverlesscookbook.domain.Response;
import tech.heartin.books.serverlesscookbook.services.SqsService;
import tech.heartin.books.serverlesscookbook.services.SqsServiceImpl;

/**
 * RequestHandler implementation.
 */
public final class LambdaSqsSdkReceiveSendBatchHandler implements RequestHandler<Request, Response> {


    private final AmazonSQS sqsClient;

    public LambdaSqsSdkReceiveSendBatchHandler() {
        this.sqsClient = AmazonSQSClientBuilder.standard()
                .withRegion(System.getenv("AWS_REGION"))
                .build();
    }

    /**
     * Handle request.
     *
     * @param request  - input to lambda handler
     * @param context - context object
     * @return greeting text
     */
    public Response handleRequest(final Request request, final Context context) {
        context.getLogger().log("Received Request: " + request);

        final SqsService sqsService =  new SqsServiceImpl(this.sqsClient);
        return sqsService.sendMessage(request, context.getLogger());

    }
}
