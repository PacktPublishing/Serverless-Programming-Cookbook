package tech.heartin.books.serverlesscookbook;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import java.util.Optional;

/**
 * RequestHandler implementation.
 */
public class SelfIntroIntentHandler implements RequestHandler {

    @Override
    public final boolean canHandle(final HandlerInput input) {
        return input.matches(Predicates.intentName("SelfIntroIntent"));
    }

    @Override
    public final Optional<Response> handle(final HandlerInput input) {
        String speechText = "Hello, this is Alexa saying intro for Heartin Kanikathottu. "
                + "Heartin is a senior software engineer and blogger with around 11 years of IT experience. "
                + "He likes to share his technical knowledge through his blogs such as CloudMaterials.com "
                + "and Java J EE dot com. "
                + "He also likes to mentor juniors and take sessions at meetups and conferences.";

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("SelfIntro", speechText)
                .build();
    }

}
