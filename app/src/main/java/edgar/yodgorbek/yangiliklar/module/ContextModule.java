package edgar.yodgorbek.yangiliklar.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import edgar.yodgorbek.yangiliklar.qualifier.ApplicationContext;
import edgar.yodgorbek.yangiliklar.scopes.ApplicationScope;

@Module
public class ContextModule {

    private Context context;


    public ContextModule(Context context) {
        this.context = context;
    }


    @Provides
    @ApplicationScope
    @ApplicationContext
    public Context provideContext() {
        return context;
    }

}
