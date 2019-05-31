package edgar.yodgorbek.sportnews.sportactivities;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import edgar.yodgorbek.sportnews.R;
import edgar.yodgorbek.sportnews.adapter.ArticleAdapter;
import edgar.yodgorbek.sportnews.adapter.EspnAdapter;
import edgar.yodgorbek.sportnews.component.ApplicationComponent;
import edgar.yodgorbek.sportnews.component.BBCSportFragmentComponent;
import edgar.yodgorbek.sportnews.component.DaggerApplicationComponent;
import edgar.yodgorbek.sportnews.component.MyApplication;
import edgar.yodgorbek.sportnews.internet.SportClient;
import edgar.yodgorbek.sportnews.model.Article;
import edgar.yodgorbek.sportnews.model.Search;
import edgar.yodgorbek.sportnews.model.SportInterface;
import edgar.yodgorbek.sportnews.model.SportNews;
import edgar.yodgorbek.sportnews.module.BBCFragmentContextModule;
import edgar.yodgorbek.sportnews.module.ContextModule;
import edgar.yodgorbek.sportnews.qualifier.ActivityContext;
import edgar.yodgorbek.sportnews.qualifier.ApplicationContext;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BBCSportFragment extends Fragment implements ArticleAdapter.ClickListener {

    public List<Article> articleList = new ArrayList<>();
    @ActivityContext
    public Context activityContext;
    Search search;
    @ApplicationContext
    public Context mContext;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    BBCSportFragmentComponent bbcSportFragmentComponent;
    BBCFragmentContextModule bbcFragmentContextModule;
    private SportNews sportNews;
    private ArticleAdapter articleAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bbcsport, container, false);
        ButterKnife.bind(this, view);
        SportInterface sportInterface = SportClient.getApiService();
        Call<SportNews> call = sportInterface.getArticles();
        call.enqueue(new Callback<SportNews>() {
            @Override
            public void onResponse(Call<SportNews> call, Response<SportNews> response) {
                if (response == null) {
                    sportNews = response.body();
                    if (sportNews != null && sportNews.getArticles() != null) {
                        articleList.addAll(sportNews.getArticles());
                    }
                    articleAdapter = new ArticleAdapter(articleList, sportNews);
                    ApplicationComponent applicationComponent;
                    applicationComponent = (ApplicationComponent) MyApplication.get(Objects.requireNonNull(getActivity())).getApplicationContext();
                    bbcSportFragmentComponent = (BBCSportFragmentComponent) DaggerApplicationComponent.builder().contextModule(new ContextModule(getContext())).build();
                    bbcSportFragmentComponent.injectBBCSportFragment(BBCSportFragment.this);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(applicationComponent));
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(articleAdapter);
                }
            }

            @Override
            public void onFailure(Call<SportNews> call, Throwable t) {

            }
        });


        SportInterface searchInterface = SportClient.getApiService();
        Call<Search> searchCall = sportInterface.getSearchViewArticles("q");
         searchCall.enqueue(new Callback<Search>() {
             @Override
             public void onResponse(Call<Search> call, Response<Search> response) {
                 search = response.body();

                 if (search != null && search.getArticles() != null) {
                     articleList.addAll(search.getArticles());
                 }

                 articleAdapter = new ArticleAdapter(articleList, search);
                 RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                 recyclerView.setLayoutManager(layoutManager);
                 recyclerView.setAdapter(articleAdapter);
             }

             @Override
             public void onFailure(Call<Search> call, Throwable t) {

             }
         });


        return view;


    }

    private Context getContext(ApplicationComponent applicationComponent) {
        return null;
    }

    public static void doFilter(String searchQuery) {

    }
}




