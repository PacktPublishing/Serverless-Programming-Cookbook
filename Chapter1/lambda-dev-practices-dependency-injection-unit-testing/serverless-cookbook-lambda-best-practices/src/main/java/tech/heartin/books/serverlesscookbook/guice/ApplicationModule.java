package tech.heartin.books.serverlesscookbook.guice;

import com.google.inject.AbstractModule;
import tech.heartin.books.serverlesscookbook.services.IAMService;
import tech.heartin.books.serverlesscookbook.services.IAMServiceImpl;

/**
 * Guice configuration class.
 */
public class ApplicationModule extends AbstractModule {

    protected final void configure() {
        bind(IAMService.class).to(IAMServiceImpl.class);
    }

}
