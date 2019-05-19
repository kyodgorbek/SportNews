package edgar.yodgorbek.sportnews.sportactivities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edgar.yodgorbek.sportnews.R;
import edgar.yodgorbek.sportnews.adapter.FoxSportsAdapter;
import edgar.yodgorbek.sportnews.internet.SportClient;
import edgar.yodgorbek.sportnews.model.Article;
import edgar.yodgorbek.sportnews.model.FoxSports;
import edgar.yodgorbek.sportnews.model.SportInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoxSportsFragment extends Fragment {

    public List<Article> articleList = new ArrayList<Article>();
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private FoxSports sportNews;
    private FoxSportsAdapter foxSportsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foxsport, container, false);
        ButterKnife.bind(this, view);
        SportInterface sportInterface = SportClient.getApiService();
        Call<FoxSports> call = sportInterface.getFoxArticles();
        call.enqueue(new Callback<FoxSports>() {
            @Override
            public void onResponse(Call<FoxSports> call, Response<FoxSports> response) {
                sportNews = response.body();
                if (sportNews != null && sportNews.getFoxArticles() != null) {
                    articleList.addAll(sportNews.getFoxArticles());
                }
                foxSportsAdapter = new FoxSportsAdapter(articleList, sportNews);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(foxSportsAdapter);
            }

            @Override
            public void onFailure(Call<FoxSports> call, Throwable t) {

            }
        });


        return view;


    }
}
