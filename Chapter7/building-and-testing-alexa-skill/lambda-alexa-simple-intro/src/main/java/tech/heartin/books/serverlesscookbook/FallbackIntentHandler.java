package tech.heartin.books.serverlesscookbook;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

/**
 * RequestHandler implementation.
 */
public class FallbackIntentHandler implements RequestHandler {

    @Override
    public final boolean canHandle(final HandlerInput input) {
        return input.matches(intentName("AMAZON.FallbackIntent"));
    }

    @Override
    public final Optional<Response> handle(final HandlerInput input) {
        String speechText = "Sorry buddy, I don't know that. You can say try saying help!";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("SelfIntro", speechText)
                .withReprompt(speechText)
                .build();
    }

}
