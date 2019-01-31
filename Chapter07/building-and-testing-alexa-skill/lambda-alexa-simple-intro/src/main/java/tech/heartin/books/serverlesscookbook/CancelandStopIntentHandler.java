package tech.heartin.books.serverlesscookbook;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

/**
 * RequestHandler implementation.
 */
public class CancelandStopIntentHandler implements RequestHandler {

    @Override
    public final boolean canHandle(final HandlerInput input) {
        return input.matches(intentName("AMAZON.StopIntent").or(intentName("AMAZON.CancelIntent")));
    }

    @Override
    public final Optional<Response> handle(final HandlerInput input) {
        return input.getResponseBuilder()
                .withSpeech("Goodbye buddy")
                .withSimpleCard("SelfIntro", "Goodbye")
                .build();
    }

}
