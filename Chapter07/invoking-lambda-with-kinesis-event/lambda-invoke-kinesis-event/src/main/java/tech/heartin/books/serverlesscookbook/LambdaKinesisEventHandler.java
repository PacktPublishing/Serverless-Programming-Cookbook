package tech.heartin.books.serverlesscookbook;

import java.nio.charset.StandardCharsets;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent;

/**
 * RequestHandler implementation.
 */
public final class LambdaKinesisEventHandler implements RequestHandler<KinesisEvent, Boolean> {


    /**
     * Handle request.
     *
     * @param kinesisEvent  - Kinesis Event passed as input to lambda handler
     * @param context - context object
     * @return true if success, else false.
     */
    public Boolean handleRequest(final KinesisEvent kinesisEvent, final Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("Received Kinesis event: " + kinesisEvent);
        logger.log("Number of records: " + kinesisEvent.getRecords().size());

        try {
            kinesisEvent.getRecords().forEach(r -> {
                final KinesisEvent.Record kr = r.getKinesis();
                logger.log("Record: " + kr.toString());
                logger.log("Data: " + StandardCharsets.UTF_8.decode(kr.getData()).toString());
            });
        } catch (final Exception e) {
            logger.log("There was an exception: " + e.getMessage());
            return false;
        }

        return true;
    }
}
