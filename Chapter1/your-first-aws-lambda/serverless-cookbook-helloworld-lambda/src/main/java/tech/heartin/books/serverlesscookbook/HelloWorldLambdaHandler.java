package tech.heartin.books.serverlesscookbook;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * RequestHandler implementation.
 */
public final class HelloWorldLambdaHandler implements RequestHandler<String, String> {

    /**
     * Handle the request.
     *
     * @param s  - input to lambda handler
     * @param context - context object
     * @return greeting text
     */
    public String handleRequest(final String s, final Context context) {
        context.getLogger().log("input: " + s + "\n");
        String greeting = "Hello " + s;
        return greeting;
    }
}
