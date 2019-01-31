package tech.heartin.books.serverlesscookbook;

import java.util.Objects;

import javax.inject.Inject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;
import tech.heartin.books.serverlesscookbook.domain.IAMOperationRequest;
import tech.heartin.books.serverlesscookbook.domain.IAMOperationResponse;
import tech.heartin.books.serverlesscookbook.guice.ApplicationModule;
import tech.heartin.books.serverlesscookbook.services.IAMService;

/**
 * RequestHandler implementation.
 */
public final class MyLambdaHandler implements RequestHandler<IAMOperationRequest, IAMOperationResponse> {

    private static final Injector INJECTOR =
            Guice.createInjector(new ApplicationModule());

    private IAMService service;

    public MyLambdaHandler() {
        INJECTOR.injectMembers(this);
        Objects.requireNonNull(service);
    }

    /**
     * Setter.
     * @param service - service.
     */
    @Inject
    public void setService(final IAMService service) {
        this.service = service;
    }


    /**
     * Handle request.
     *
     * @param request  - input to lambda handler
     * @param context - context object
     * @return greeting text
     */
    public IAMOperationResponse handleRequest(final IAMOperationRequest request, final Context context) {
        context.getLogger().log("Requested operation = " + request.getOperation()
                + ". User name = " + request.getUserName());

        switch (request.getOperation()) {
            case "CREATE" :
                return this.service.createUser(request.getUserName());
            case "CHECK" :
                return  this.service.checkUser(request.getUserName());
            case "DELETE" :
                return this.service.deleteUser(request.getUserName());

                default:
                    return new IAMOperationResponse(null,
                            "Invalid operation " + request.getOperation()
                                    + ". Allowed: CREATE, CHECK, DELETE.");

        }
    }
}
