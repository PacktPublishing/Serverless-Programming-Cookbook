package tech.heartin.books.serverlesscookbook.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Response POJO.
 */
@Data
@AllArgsConstructor
public class Response {
    private String message;
}
