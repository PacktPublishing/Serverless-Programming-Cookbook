package tech.heartin.books.serverlesscookbook.services;

import java.util.ArrayList;
import java.util.Collection;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.lambda.runtime.events.SNSEvent.SNSRecord;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;

/**
 * Implementation class for SnsService.
 */
public class SnsServiceImpl implements SnsService {

    private final AmazonSQS sqsClient;

    public SnsServiceImpl(final AmazonSQS sqsClient) {
        this.sqsClient = sqsClient;
    }

    @Override
    public final Boolean processEvent(final SNSEvent event, final String outputQueueURL, final LambdaLogger logger) {

        try {

            logger.log("Number of records in event: " + event.getRecords().size());

            Collection<SendMessageBatchRequestEntry> entries = new ArrayList<>();

            int idVal = 1;
            for (SNSRecord r : event.getRecords()) {
                logger.log("Adding message: " + r.getSNS().getMessage());
                entries.add(new SendMessageBatchRequestEntry("id_" + idVal, r.getSNS().getMessage()));
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
