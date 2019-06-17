package edgar.yodgorbek.yangiliklar.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import edgar.yodgorbek.yangiliklar.qualifier.ActivityContext;
import edgar.yodgorbek.yangiliklar.scopes.ActivityScope;
import edgar.yodgorbek.yangiliklar.sportactivities.BBCSportFragment;

@Module
public class BBCFragmentContextModule {

    public Context context;
    private BBCSportFragment bbcSportFragment;


    public BBCFragmentContextModule(Context context, BBCSportFragment bbcSportFragment) {
        this.bbcSportFragment = bbcSportFragment;
        this.context = context;
    }

    @Provides
    @ActivityScope
    @ActivityContext

    public BBCSportFragment providesBBCSportFragment() {
        return bbcSportFragment;
    }

    @Provides
    @ActivityScope
    @ActivityContext
    public Context provideContext() {
        return context;
    }
}


