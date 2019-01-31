package tech.heartin.books.serverlesscookbook;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.SessionEndedRequest;
import static com.amazon.ask.request.Predicates.requestType;

import java.util.Optional;

/**
 * RequestHandler implementation.
 */
public class SessionEndedRequestHandler implements RequestHandler {

    @Override
    public final boolean canHandle(final HandlerInput input) {
        return input.matches(requestType(SessionEndedRequest.class));
    }

    @Override
    public final Optional<Response> handle(final HandlerInput input) {
        //any cleanup logic goes here
        return input.getResponseBuilder().build();
    }

}
