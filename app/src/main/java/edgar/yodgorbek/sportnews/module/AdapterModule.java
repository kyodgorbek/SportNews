package edgar.yodgorbek.sportnews.module;


import dagger.Module;
import dagger.Provides;
import edgar.yodgorbek.sportnews.adapter.ArticleAdapter;
import edgar.yodgorbek.sportnews.scopes.ActivityScope;
import edgar.yodgorbek.sportnews.sportactivities.BBCSportFragment;

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
