package tech.heartin.books.serverlesscookbook.services;

import tech.heartin.books.serverlesscookbook.domain.Request;
import tech.heartin.books.serverlesscookbook.domain.Response;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.DescribeStreamResult;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsRequestEntry;
import com.amazonaws.services.kinesis.model.PutRecordsResult;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.util.StringUtils;

/**
 * Implementation class for KinesisService.
 */
public class KinesisServiceImpl implements KinesisService {

    private final AmazonKinesis kinesisClient;
    private final List<PutRecordsRequestEntry> kinesisBatch;
    private static final String ERROR_MESSAGE = "Request completed with errors. Check Lambda logs for more details.";
    private static final String SUCCESS_MESSAGE = "Request completed without errors.";

    private boolean isError = false;
    private int documentAddedCount;


    public KinesisServiceImpl(final AmazonKinesis kinesisClient) {
        this.kinesisClient = kinesisClient;
        this.kinesisBatch = new ArrayList<>();
    }

    @Override
    public final Response addRecords(final Request request, final LambdaLogger logger) {

        this.documentAddedCount = request.getCount();

        DescribeStreamResult result = this.kinesisClient.describeStream(request.getStreamName());
        logger.log("Stream Status: " + result.getStreamDescription().getStreamStatus() + ". ");

        logger.log("Adding records to Stream...");

        String payload;

        for (int i = 1; i <= request.getCount(); i++) {

            payload = request.getPayload() + i;

            this.kinesisBatch.add(new PutRecordsRequestEntry()
                    .withPartitionKey(request.getPartitionKey())
                    .withData(ByteBuffer.wrap(payload.getBytes())));

            if (this.kinesisBatch.size() >= request.getBatchSize()) {

                try {
                    logger.log("Flushing records to Stream...");
                    flushBatch(request.getStreamName(), logger);
                } catch (Exception e) {
                    logger.log("Exception occurred: " + e);
                    this.isError = false;
                } finally {
                    this.kinesisBatch.clear();
                }
            }

        }

        if (this.isError) {
            return new Response(ERROR_MESSAGE, documentAddedCount);
        } else {
            return new Response(SUCCESS_MESSAGE, documentAddedCount);
        }
    }

    private void flushBatch(final String streamName, final LambdaLogger logger) {
        final PutRecordsResult result = this.kinesisClient.putRecords(new PutRecordsRequest()
                .withStreamName(streamName)
                .withRecords(this.kinesisBatch));

        result.getRecords().forEach(r -> {
            if (!(StringUtils.hasValue(r.getErrorCode()))) {
                String successMessage = "Successfully processed record with sequence number: " + r.getSequenceNumber()
                        + ", shard id: " + r.getShardId();
                logger.log(successMessage);
            } else {
                this.documentAddedCount--;

                String errorMessage = "Did not process record with sequence number: " + r.getSequenceNumber()
                        + ", error code: " + r.getErrorCode()
                        + ", error message: " + r.getErrorMessage();
                logger.log(errorMessage);
                this.isError = true;
            }
        });


        // You may also implement a retry logic only for failed records (e.g. Create a list for failed records,
        // add error records to that list and finally retry all failed records until a max retry count is reached.)
        /*
        if (result.getFailedRecordCount() != null && result.getFailedRecordCount() > 0) {
            result.getRecords().forEach(r -> {
                if ((r != null) && (StringUtils.hasValue(r.getErrorCode()))) {
                    // add this record to the retry list.
                }
            });
        }
        */
    }
}
