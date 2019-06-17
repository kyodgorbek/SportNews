package edgar.yodgorbek.yangiliklar.component;

import android.content.Context;

import dagger.Component;
import edgar.yodgorbek.yangiliklar.internet.SportClient;
import edgar.yodgorbek.yangiliklar.model.SportInterface;
import edgar.yodgorbek.yangiliklar.module.ContextModule;
import edgar.yodgorbek.yangiliklar.qualifier.ApplicationContext;
import edgar.yodgorbek.yangiliklar.scopes.ApplicationScope;

@ApplicationScope
@Component(modules = {ContextModule.class, SportClient.class})
public interface ApplicationComponent {


    public SportInterface sportInterface();

    @ApplicationContext
    public Context getContext();

    public void injectApplication(MyApplication myApplication);


}
