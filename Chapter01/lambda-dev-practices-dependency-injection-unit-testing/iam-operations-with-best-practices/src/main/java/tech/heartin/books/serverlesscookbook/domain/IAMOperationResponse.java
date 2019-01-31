package tech.heartin.books.serverlesscookbook.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Response POJO.
 */
@Data
@AllArgsConstructor
public class IAMOperationResponse {
    private String message;
    private String errorMessage;
}
