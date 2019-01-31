package tech.heartin.books.serverlesscookbook.domain;

import lombok.Data;

/**
 * Request POJO.
 */
@Data
public class HandlerRequest {
    private String name;
    private String time;
}
