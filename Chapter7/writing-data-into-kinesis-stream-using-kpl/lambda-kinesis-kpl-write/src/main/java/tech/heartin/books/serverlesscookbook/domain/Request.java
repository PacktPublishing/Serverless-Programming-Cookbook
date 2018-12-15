package tech.heartin.books.serverlesscookbook.domain;

import lombok.Data;

/**
 * Request POJO.
 */
@Data
public class Request {
    private String streamName;
    private String payload;
    private String  count;
    private String batchSize;
}
