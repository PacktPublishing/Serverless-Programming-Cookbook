package tech.heartin.books.serverlesscookbook.domain;

import lombok.Data;

/**
 * Request POJO.
 */
@Data
public class Request {
    private String queueName;
    private String message;
}
