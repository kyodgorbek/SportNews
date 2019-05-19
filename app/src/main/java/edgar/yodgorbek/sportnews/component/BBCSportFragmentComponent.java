package edgar.yodgorbek.sportnews.component;

import dagger.Component;
import edgar.yodgorbek.sportnews.module.AdapterModule;
import edgar.yodgorbek.sportnews.qualifier.ActivityContext;
import edgar.yodgorbek.sportnews.scopes.ActivityScope;
import edgar.yodgorbek.sportnews.sportactivities.BBCSportFragment;

@ActivityScope
@ActivityContext
@Component(modules = AdapterModule.class, dependencies = ApplicationComponent.class)
public interface BBCSportFragmentComponent {

    void injectBBCSportFragment(BBCSportFragment bbcSportFragment);
}
