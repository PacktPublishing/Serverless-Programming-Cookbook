package tech.heartin.books.serverlesscookbook;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

/**
 * RequestHandler implementation.
 */
public class HelpIntentHandler implements RequestHandler {

    @Override
    public final boolean canHandle(final HandlerInput input) {
        return input.matches(intentName("AMAZON.HelpIntent"));
    }

    @Override
    public final Optional<Response> handle(final HandlerInput input) {
        String speechText = "You you may say 'please say intro'!";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("SelfIntro", speechText)
                .withReprompt(speechText)
                .build();
    }
}
