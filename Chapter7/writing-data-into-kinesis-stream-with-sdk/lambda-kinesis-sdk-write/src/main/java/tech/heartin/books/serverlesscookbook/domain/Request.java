package tech.heartin.books.serverlesscookbook.domain;

import lombok.Data;

/**
 * Request POJO.
 */
@Data
public class Request {
    private String streamName;
    private String partitionKey;
    private String payload;
    private int  count;
    private int batchSize;
}
