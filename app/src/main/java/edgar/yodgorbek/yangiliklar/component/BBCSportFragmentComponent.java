package edgar.yodgorbek.yangiliklar.component;

import dagger.Component;
import edgar.yodgorbek.yangiliklar.module.AdapterModule;
import edgar.yodgorbek.yangiliklar.qualifier.ActivityContext;
import edgar.yodgorbek.yangiliklar.scopes.ActivityScope;
import edgar.yodgorbek.yangiliklar.sportactivities.BBCSportFragment;

@ActivityScope
@ActivityContext
@Component(modules = AdapterModule.class, dependencies = ApplicationComponent.class)
public interface BBCSportFragmentComponent {

    void injectBBCSportFragment(BBCSportFragment bbcSportFragment);
}
