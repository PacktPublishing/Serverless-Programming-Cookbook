package tech.heartin.books.serverlesscookbook.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;

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
    public final Response sendMessage(final Request request, final LambdaLogger logger) {

        try {

            final ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest()
                    .withQueueUrl(request.getInputQueueURL())
                    .withMaxNumberOfMessages(request.getMaxMessagesToReceive());

            final List<Message> messages = this.sqsClient.receiveMessage(receiveMessageRequest).getMessages();

            logger.log("Number of messages: " + messages.size());

            Collection<SendMessageBatchRequestEntry> entries = new ArrayList<>();

            int idVal = 1;
            for (Message m : messages) {
                logger.log("Adding message: " + m.getBody());
                entries.add(new SendMessageBatchRequestEntry("id_" + idVal,
                        m.getBody()).withDelaySeconds(request.getDelay()));
                idVal++;
            }

            final SendMessageBatchRequest sendBatchRequest = new SendMessageBatchRequest()
                    .withQueueUrl(request.getOutputQueueURL())
                    .withEntries(entries);
            this.sqsClient.sendMessageBatch(sendBatchRequest);


            // delete messages from the queue with receipt handle.
            for (Message m : messages) {
                this.sqsClient.deleteMessage(request.getInputQueueURL(), m.getReceiptHandle());
            }
        } catch (Exception e) {
            final String errorMessage = "Error occurred: " + e.getMessage();
            logger.log(errorMessage);
            return new Response(errorMessage);
        }

        return new Response("Message forwarded successfully");

    }


}
