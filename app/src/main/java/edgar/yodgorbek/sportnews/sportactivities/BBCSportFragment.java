package edgar.yodgorbek.sportnews.sportactivities;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edgar.yodgorbek.sportnews.R;
import edgar.yodgorbek.sportnews.adapter.ArticleAdapter;
import edgar.yodgorbek.sportnews.component.ApplicationComponent;
import edgar.yodgorbek.sportnews.component.BBCSportFragmentComponent;
import edgar.yodgorbek.sportnews.internet.SportClient;
import edgar.yodgorbek.sportnews.model.Article;
import edgar.yodgorbek.sportnews.model.Search;
import edgar.yodgorbek.sportnews.model.SportInterface;
import edgar.yodgorbek.sportnews.model.SportNews;
import edgar.yodgorbek.sportnews.module.BBCFragmentContextModule;
import edgar.yodgorbek.sportnews.qualifier.ActivityContext;
import edgar.yodgorbek.sportnews.qualifier.ApplicationContext;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BBCSportFragment extends Fragment implements ArticleAdapter.ClickListener {

    public List<Article> articleList = new ArrayList<>();


    @ActivityContext
    public Context activityContext;
    @ApplicationContext
    public Context mContext;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    BBCSportFragmentComponent bbcSportFragmentComponent;
    BBCFragmentContextModule bbcFragmentContextModule;
    private Search search;
    private SportNews sportNews;
    private ArticleAdapter articleAdapter;
    private SportInterface apiInterface;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bbcsport, container, false);
        ButterKnife.bind(this, view);

        articleAdapter = new ArticleAdapter(articleList, sportNews);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(/*applicationComponent*/));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(articleAdapter);

        apiInterface = SportClient.getApiService();

        fetchInitialArticles();

        return view;
    }

    private void fetchInitialArticles() {
        ProgressDialog progress = new ProgressDialog(getContext());
        progress.setMessage("Loading... ");
        progress.setIndeterminate(true);
        progress.show();
        Call<SportNews> call = apiInterface.getArticles();
        call.enqueue(new Callback<SportNews>() {
            @Override
            public void onResponse(Call<SportNews> call, Response<SportNews> response) {
                if (response.body() != null) {
                    sportNews = response.body();
                    if (sportNews.getArticles() != null) {
                        articleList.clear();
                        articleList.addAll(sportNews.getArticles());
                    }
                    articleAdapter.notifyDataSetChanged();
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<SportNews> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Context getContext(ApplicationComponent applicationComponent) {
        return null;
    }

    public void doFilter(String searchQuery) {
        searchAPICall(searchQuery);

    }

    private void searchAPICall(String searchQuery) {
        ProgressDialog progress = new ProgressDialog(getContext());
        progress.setMessage("Searching... ");
        progress.setIndeterminate(true);
        progress.show();

        Call<Search> searchCall = apiInterface.getSearchViewArticles(searchQuery);
        searchCall.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                try {
                    search = response.body();

                    if (search != null && search.getArticles() != null) {
                        articleList.clear();
                        articleList.addAll(search.getArticles());
                    }
                    articleAdapter.notifyDataSetChanged();

                    Toast.makeText(getContext(), "Searched.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

