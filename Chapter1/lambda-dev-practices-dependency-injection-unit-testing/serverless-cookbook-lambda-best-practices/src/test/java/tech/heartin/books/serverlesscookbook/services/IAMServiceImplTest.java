package tech.heartin.books.serverlesscookbook.services;

import java.util.Objects;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.CreateUserResult;
import com.amazonaws.services.identitymanagement.model.DeleteUserResult;
import com.amazonaws.services.identitymanagement.model.ListUsersResult;
import com.amazonaws.services.identitymanagement.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import tech.heartin.books.serverlesscookbook.domain.IAMOperationResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IAMServiceImplTest {

    @Mock
    private AmazonIdentityManagement iamClient;

    private IAMService service;

    @Before
    public void setUp() {
        service = new IAMServiceImpl(iamClient);
        Objects.requireNonNull(service);
    }

    @Test
    public void testCreateUser() {
        IAMOperationResponse expectedResponse = new IAMOperationResponse(
                "Created user test_user", null);
        when(iamClient.createUser(any()))
                .thenReturn(new CreateUserResult()
                        .withUser(new User().withUserName("test_user")));
        IAMOperationResponse actualResponse
                = service.createUser("test_user");
        Assert.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testCheckUser() {
        IAMOperationResponse expectedResponse = new IAMOperationResponse(
                "User test_user exist", null);
        when(iamClient.listUsers(any()))
                .thenReturn(getListUsersResult());
        IAMOperationResponse actualResponse
                = service.checkUser("test_user");
        Assert.assertEquals(expectedResponse, actualResponse);
    }

    private ListUsersResult getListUsersResult() {
        ListUsersResult result = new ListUsersResult();
        result.getUsers().add(new User().withUserName("test_user"));
        return result;
    }

    @Test
    public void testDeleteUser() {
        IAMOperationResponse expectedResponse = new IAMOperationResponse(
                "Deleted user test_user", null);
        when(iamClient.deleteUser(any()))
                .thenReturn(new DeleteUserResult());
        IAMOperationResponse actualResponse
                = service.deleteUser("test_user");
        Assert.assertEquals(expectedResponse, actualResponse);
    }
}
