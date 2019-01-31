package tech.heartin.books.serverlesscookbook;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import tech.heartin.books.serverlesscookbook.domain.Request;
import tech.heartin.books.serverlesscookbook.domain.Response;
import tech.heartin.books.serverlesscookbook.services.DynamoDBService;
import tech.heartin.books.serverlesscookbook.services.DynamoDBServiceImpl1;
import tech.heartin.books.serverlesscookbook.services.DynamoDBServiceImpl2;

/**
 * RequestHandler implementation.
 */
public final class MyLambdaHandler implements RequestHandler<Request, Response> {

    private DynamoDBService service;

    /**
     * Handle request.
     *
     * @param request  - input to lambda handler
     * @param context - context object
     * @return greeting text
     */
    public Response handleRequest(final Request request, final Context context) {
        context.getLogger().log("Put item on table " + request.getTableName());

        final String version = System.getenv("API_VERSION");
        if (version != null && version.equals("V2")) {
            service = new DynamoDBServiceImpl2();
        } else {
            service = new DynamoDBServiceImpl1();
        }

        if (isNotEmpty(request.getPartitionKeyValue()) && isNotEmpty(request.getSortKeyValue())) {
            return service.getItem(request);
        } else if (isNotEmpty(request.getPartitionKeyValue())) {
            return service.query(request);
        } else {
            return service.scan(request);
        }
    }

    private boolean isNotEmpty(final String str) {
        return str != null && !str.isEmpty();
    }
}
