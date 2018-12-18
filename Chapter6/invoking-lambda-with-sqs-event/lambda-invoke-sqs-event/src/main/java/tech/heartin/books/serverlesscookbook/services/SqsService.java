package tech.heartin.books.serverlesscookbook.services;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

/**
 * Service class interface for Kinesis operations.
 */
public interface SqsService {

    /**
     * Add records to Kinesis Data Stream.
     * @param event SQS event received.
     * @param outputQueueURL Output queue URL.
     * @param logger Lambda Logger from context.
     * @return Response domain object.
     */
    Boolean processEvent(SQSEvent event, String outputQueueURL, LambdaLogger logger);
}
