package tech.heartin.books.serverlesscookbook.domain;

import lombok.Data;

/**
 * Request POJO.
 */
@Data
public class IAMOperationRequest {
    private String operation;
    private String userName;
}
