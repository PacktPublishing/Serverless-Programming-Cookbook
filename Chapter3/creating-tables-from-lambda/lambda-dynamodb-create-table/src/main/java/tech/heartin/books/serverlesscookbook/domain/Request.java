package tech.heartin.books.serverlesscookbook.domain;

import lombok.Data;

/**
 * Request POJO.
 */
@Data
public class Request {
    private String tableName;
    private String partitionKey;
    private String sortKey;
    private long readCapacityUnits;
    private long writeCapacityUnits;
    private boolean waitForActive;
}
