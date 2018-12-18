package tech.heartin.books.serverlesscookbook.services;

import tech.heartin.books.serverlesscookbook.domain.Request;
import tech.heartin.books.serverlesscookbook.domain.Response;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

/**
 * Service class interface for SQS operations.
 */
public interface SqsService {

    /**
     * Create queue and send message to SQS queue.
     * @param request Request domain object.
     * @param logger Lambda Logger from context.
     * @return Response domain object.
     */
    Response createQueueAndSendMessage(Request request, LambdaLogger logger);

}
