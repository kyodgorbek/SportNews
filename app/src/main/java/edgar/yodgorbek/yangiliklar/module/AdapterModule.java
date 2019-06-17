package edgar.yodgorbek.yangiliklar.module;


import dagger.Module;
import dagger.Provides;
import edgar.yodgorbek.yangiliklar.scopes.ActivityScope;
import edgar.yodgorbek.yangiliklar.sportactivities.BBCSportFragment;

@Module(includes = {BBCFragmentContextModule.class})
public class AdapterModule {

    @Provides
    @ActivityScope
    public ArticleAdapter getArticles(ArticleAdapter.ClickListener clickListener) {
        return new ArticleAdapter(clickListener);
    }


    @Provides
    @ActivityScope
    public BBCSportFragment getClickListener(BBCSportFragment bbcSportFragment) {
        return bbcSportFragment;
    }

}
