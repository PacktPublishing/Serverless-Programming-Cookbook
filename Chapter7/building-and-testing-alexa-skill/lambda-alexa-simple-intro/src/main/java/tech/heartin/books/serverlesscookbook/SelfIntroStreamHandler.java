package tech.heartin.books.serverlesscookbook;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.SkillStreamHandler;

/**
 * RequestHandler implementation.
 */
public class SelfIntroStreamHandler extends SkillStreamHandler {

    private static Skill skill = Skills.standard()
            .addRequestHandlers(
                    new CancelandStopIntentHandler(),
                    new SelfIntroIntentHandler(),
                    new HelpIntentHandler(),
                    new LaunchRequestHandler(),
                    new SessionEndedRequestHandler())
            .build();

    public SelfIntroStreamHandler() {
        super(skill);
    }

}
