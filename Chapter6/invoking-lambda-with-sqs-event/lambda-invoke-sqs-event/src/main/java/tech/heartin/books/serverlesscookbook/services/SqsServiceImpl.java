package tech.heartin.books.serverlesscookbook.services;

import java.util.ArrayList;
import java.util.Collection;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;

/**
 * Implementation class for SqsService.
 */
public class SqsServiceImpl implements SqsService {

    private final AmazonSQS sqsClient;

    public SqsServiceImpl(final AmazonSQS sqsClient) {
        this.sqsClient = sqsClient;
    }

    @Override
    public final Boolean processEvent(final SQSEvent event, final String outputQueueURL, final LambdaLogger logger) {

        try {

            logger.log("Number of messages in event: " + event.getRecords().size());

            logger.log("Output Queue URL: " + outputQueueURL);

            Collection<SendMessageBatchRequestEntry> entries = new ArrayList<>();

            int idVal = 1;
            for (SQSMessage m : event.getRecords()) {
                logger.log("Adding message: " + m.getBody());
                entries.add(new SendMessageBatchRequestEntry("id_" + idVal, m.getBody()));
                idVal++;
            }

            final SendMessageBatchRequest sendBatchRequest = new SendMessageBatchRequest()
                    .withQueueUrl(outputQueueURL)
                    .withEntries(entries);
            this.sqsClient.sendMessageBatch(sendBatchRequest);

        } catch (Exception e) {
            final String errorMessage = "Error occurred: " + e.getMessage();
            logger.log(errorMessage);
            return false;
        }

        return true;

    }

}
