package tech.heartin.books.serverlesscookbook;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import tech.heartin.books.serverlesscookbook.domain.Request;
import tech.heartin.books.serverlesscookbook.domain.Response;
import tech.heartin.books.serverlesscookbook.services.KinesisService;
import tech.heartin.books.serverlesscookbook.services.KinesisServiceImpl;

/**
 * RequestHandler implementation.
 */
public final class LambdaKinesisSdkWriteHandler implements RequestHandler<Request, Response> {


    private final AmazonKinesis kinesisClient;

    public LambdaKinesisSdkWriteHandler() {
        this.kinesisClient = AmazonKinesisClientBuilder.standard()
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

        final KinesisService kinesisService =  new KinesisServiceImpl(this.kinesisClient);
        return kinesisService.addRecords(request, context.getLogger());

    }
}
