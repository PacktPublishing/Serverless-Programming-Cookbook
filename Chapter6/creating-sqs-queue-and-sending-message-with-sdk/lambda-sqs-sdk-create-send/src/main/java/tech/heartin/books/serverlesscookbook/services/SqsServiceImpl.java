package tech.heartin.books.serverlesscookbook.services;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import tech.heartin.books.serverlesscookbook.domain.Request;
import tech.heartin.books.serverlesscookbook.domain.Response;

/**
 * Implementation class for SqsService.
 */
public class SqsServiceImpl implements SqsService {

    private final AmazonSQS sqsClient;

    public SqsServiceImpl(final AmazonSQS sqsClient) {
        this.sqsClient = sqsClient;
    }

    @Override
    public final Response createQueueAndSendMessage(final Request request, final LambdaLogger logger) {

        String errorMessage;

        try {
            CreateQueueResult createResult = this.sqsClient.createQueue(request.getQueueName());
            logger.log("Created queue: " + createResult.getQueueUrl());

        } catch (AmazonSQSException e) {

            if (e.getErrorCode().equals("QueueAlreadyExists")) {
                errorMessage = "QueueAlreadyExists: " + request.getQueueName();
            } else {
                errorMessage = "Error during queue creation: " + e.getErrorCode();
            }

            logger.log(errorMessage);
            return new Response(errorMessage);
        }

        final String queueUrl = this.sqsClient.getQueueUrl(request.getQueueName()).getQueueUrl();

        final SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(request.getMessage())
                .withDelaySeconds(5);
        try {
            this.sqsClient.sendMessage(sendMessageRequest);
        } catch (Exception e) {
            errorMessage = "Exception while sending message: " + e.getMessage();
            logger.log(errorMessage);
            return new Response(errorMessage);
        }

        return new Response("Successfully sent message to queue: " + request.getQueueName());

    }

}
