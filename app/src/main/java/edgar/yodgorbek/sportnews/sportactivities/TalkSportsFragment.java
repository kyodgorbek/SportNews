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
import edgar.yodgorbek.sportnews.adapter.TalkSportAdapter;
import edgar.yodgorbek.sportnews.internet.SportClient;
import edgar.yodgorbek.sportnews.model.Article;
import edgar.yodgorbek.sportnews.model.SportInterface;
import edgar.yodgorbek.sportnews.model.TalkSports;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TalkSportsFragment extends Fragment {


    public List<Article> articleList = new ArrayList<Article>();
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private TalkSports talkSports;
    private TalkSportAdapter talkSportAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bbcsport, container, false);
        ButterKnife.bind(this, view);
        SportInterface sportInterface = SportClient.getApiService();
        Call<TalkSports> call = sportInterface.getSportArticles();
        call.enqueue(new Callback<TalkSports>() {
            @Override
            public void onResponse(Call<TalkSports> call, Response<TalkSports> response) {
                talkSports = response.body();
                if (talkSports != null && talkSports.getSportArticles() != null) {
                    articleList.addAll(talkSports.getSportArticles());
                }
                talkSportAdapter = new TalkSportAdapter(articleList, talkSports);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(talkSportAdapter);
            }

            @Override
            public void onFailure(Call<TalkSports> call, Throwable t) {

            }
        });


        return view;


    }

}
