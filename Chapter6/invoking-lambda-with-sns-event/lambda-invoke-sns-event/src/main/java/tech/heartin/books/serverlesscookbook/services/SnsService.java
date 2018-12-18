package tech.heartin.books.serverlesscookbook.services;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

/**
 * Service class interface for SNS operations.
 */
public interface SnsService {

    /**
     * Process SNSEvent.
     * @param event SQS event received.
     * @param outputQueueURL Output queue URL.
     * @param logger Lambda Logger from context.
     * @return Response domain object.
     */
    Boolean processEvent(SNSEvent event, String outputQueueURL, LambdaLogger logger);
}
