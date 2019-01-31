package tech.heartin.books.serverlesscookbook.domain;

import java.util.Map;

import lombok.Data;

/**
 * Request POJO.
 */
@Data
public class Request {
    private String tableName;
    private String partitionKey;
    private String sortKey;
    private String partitionKeyValue;
    private String sortKeyValue; // Will be stored integer.
    private boolean waitForActive;
    private Map<String, String> filterData;
}
