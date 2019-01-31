package tech.heartin.books.serverlesscookbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;

import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

/**
 *  Proxy Stream Handler.
 */
public class ProxyStreamHandlerLambda implements RequestStreamHandler {

    /**
     * handleRequest implementation.
     * @param inputStream - Input stream from API Gateway.
     * @param outputStream - Output stream to API Gateway.
     * @param context - Context.
     * @throws IOException - If something goes wrong.
     */
    public final void handleRequest(final InputStream inputStream,
                                    final OutputStream outputStream,
                                    final Context context) throws IOException {

        LambdaLogger logger = context.getLogger();
        context.getLogger().log("Inside Proxy Stream Handler.");

        final String greeting = generateGreetingFromInputStream(inputStream);
        final JSONObject responseJson = generateResponseJson(greeting);

        logger.log(responseJson.toJSONString());

        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
        writer.write(responseJson.toJSONString());
        writer.close();
    }

    private String generateGreetingFromInputStream(final InputStream inputStream) {

        final JSONParser parser = new JSONParser();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String name = "User";
        String application = "";
        String time = "Day";
        String userAgent = "";

        try {
            final JSONObject event = (JSONObject) parser.parse(reader);

            if (event.get("pathParameters") != null) {
                JSONObject pathParams = (JSONObject) event.get("pathParameters");
                if (pathParams.get("proxy") != null) {
                    application = (String) pathParams.get("proxy");
                }
            }

            if (event.get("queryStringParameters") != null) {
                JSONObject queryParams = (JSONObject) event.get("queryStringParameters");
                if (queryParams.get("name") != null) {
                    name = (String) queryParams.get("name");
                }
            }

            if (event.get("body") != null) {
                JSONObject body = (JSONObject) parser.parse((String) event.get("body"));
                if (body.get("time") != null) {
                    time = (String) body.get("time");
                }
            }

            if (event.get("headers") != null) {
                JSONObject headers = (JSONObject) event.get("headers");
                if (headers.get("User-Agent") != null) {
                    userAgent = (String) headers.get("User-Agent");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String greeting = "Good " + time + ", " + name + ". ";
        greeting += "Welcome to " + application + ". ";
        if (!userAgent.equals("")) {
            greeting += "Client User-Agent is " + userAgent + ".";
        }

        return greeting;
    }

    private JSONObject generateResponseJson(final String greeting) {

        JSONObject responseBody = new JSONObject();
        responseBody.put("message", greeting);

        JSONObject headers = new JSONObject();
        //Not required, shown to demo the possibility.
        headers.put("Content-Type", "application/json");

        JSONObject responseJson = new JSONObject();
        responseJson.put("isBase64Encoded", false);
        responseJson.put("statusCode", "200");
        responseJson.put("headers", headers);
        responseJson.put("body", responseBody.toString());

        return responseJson;

    }
}
