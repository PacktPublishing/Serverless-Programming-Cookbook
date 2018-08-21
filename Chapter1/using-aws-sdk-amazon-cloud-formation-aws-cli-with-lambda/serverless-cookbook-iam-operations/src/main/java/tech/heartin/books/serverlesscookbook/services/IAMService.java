package tech.heartin.books.serverlesscookbook.services;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.CreateUserRequest;
import com.amazonaws.services.identitymanagement.model.CreateUserResult;
import com.amazonaws.services.identitymanagement.model.DeleteConflictException;
import com.amazonaws.services.identitymanagement.model.DeleteUserRequest;
import com.amazonaws.services.identitymanagement.model.ListUsersRequest;
import com.amazonaws.services.identitymanagement.model.ListUsersResult;
import com.amazonaws.services.identitymanagement.model.User;

import tech.heartin.books.serverlesscookbook.domain.IAMOperationResponse;

/**
 * Service class for IAM operations.
 */
public class IAMService {

    private final AmazonIdentityManagement iamClient;

    public IAMService() {
        iamClient = AmazonIdentityManagementClientBuilder.defaultClient();
    }

    /**
     * Create user.
     * @param userName - user name.
     * @return IAMOperationResponse
     */
    public final IAMOperationResponse createUser(final String userName) {

        CreateUserRequest request =
                new CreateUserRequest().withUserName(userName);

        CreateUserResult response = iamClient.createUser(request);

        return new IAMOperationResponse(
                "Created user " + response.getUser().getUserName(),
                null);
    }

    /**
     * Check user.
     * @param userName - user name.
     * @return IAMOperationResponse
     */
    public final IAMOperationResponse checkUser(final String userName) {
        boolean done = false;
        ListUsersRequest request = new ListUsersRequest();

        while (!done) {
            ListUsersResult response = iamClient.listUsers(request);

            for (User user : response.getUsers()) {
                if (user.getUserName().equals(userName)) {
                    return new IAMOperationResponse("User " + userName + " exist", null);
                }
            }

            request.setMarker(response.getMarker());

            if (!response.getIsTruncated()) {
                done = true;
            }
        }
        return new IAMOperationResponse(null, "User " + userName + " does not exist");
    }

    /**
     * Delete user.
     * @param userName - user name.
     * @return IAMOperationResponse
     */
    public final IAMOperationResponse deleteUser(final String userName) {
        DeleteUserRequest request = new DeleteUserRequest()
                .withUserName(userName);

        try {
            iamClient.deleteUser(request);
        } catch (DeleteConflictException e) {
            return new IAMOperationResponse(null,
                    "Unable to delete user");
        }

        return new IAMOperationResponse(
                "Deleted user " + userName,
                null);
    }

}
