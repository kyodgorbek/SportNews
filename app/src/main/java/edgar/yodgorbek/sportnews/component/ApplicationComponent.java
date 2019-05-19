package edgar.yodgorbek.sportnews.component;

import android.content.Context;

import dagger.Component;
import edgar.yodgorbek.sportnews.internet.SportClient;
import edgar.yodgorbek.sportnews.model.SportInterface;
import edgar.yodgorbek.sportnews.module.ContextModule;
import edgar.yodgorbek.sportnews.qualifier.ApplicationContext;
import edgar.yodgorbek.sportnews.scopes.ApplicationScope;

@ApplicationScope
@Component(modules = {ContextModule.class, SportClient.class})
public interface ApplicationComponent {


    public SportInterface sportInterface();

    @ApplicationContext
    public Context getContext();

    public void injectApplication(MyApplication myApplication);


}
