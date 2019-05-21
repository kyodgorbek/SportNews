package edgar.yodgorbek.sportnews.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import edgar.yodgorbek.sportnews.qualifier.ActivityContext;
import edgar.yodgorbek.sportnews.scopes.ActivityScope;
import edgar.yodgorbek.sportnews.sportactivities.BBCSportFragment;

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


