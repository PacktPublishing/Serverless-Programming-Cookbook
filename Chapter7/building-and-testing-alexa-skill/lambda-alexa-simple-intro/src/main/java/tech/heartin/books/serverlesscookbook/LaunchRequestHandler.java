package tech.heartin.books.serverlesscookbook;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import java.util.Optional;

/**
 * RequestHandler implementation.
 */
public class LaunchRequestHandler implements RequestHandler {

    @Override
    public final boolean canHandle(final HandlerInput input) {
        return input.matches(Predicates.requestType(LaunchRequest.class));
    }

    @Override
    public final Optional<Response> handle(final HandlerInput input) {
        String speechText = "Welcome to the Self Intro Alexa Skill for Heartin, you may say 'please say intro'";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("SelfIntro", speechText)
                .withReprompt(speechText)
                .build();
    }
}
