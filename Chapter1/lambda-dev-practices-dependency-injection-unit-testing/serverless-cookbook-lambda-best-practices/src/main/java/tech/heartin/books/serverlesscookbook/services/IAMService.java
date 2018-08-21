package tech.heartin.books.serverlesscookbook.services;

import tech.heartin.books.serverlesscookbook.domain.IAMOperationResponse;

/**
 * Interface for IAM operations.
 */
public interface IAMService {
    /**
     * Create user.
     * @param userName - user name.
     * @return IAMOperationResponse
     */
    IAMOperationResponse createUser(String userName);

    /**
     * Check user.
     * @param userName - user name.
     * @return IAMOperationResponse
     */
    IAMOperationResponse checkUser(String userName);

    /**
     * Delete user.
     * @param userName - user name.
     * @return IAMOperationResponse
     */
    IAMOperationResponse deleteUser(String userName);
}
