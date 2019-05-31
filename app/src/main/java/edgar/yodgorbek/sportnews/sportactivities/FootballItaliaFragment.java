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
import edgar.yodgorbek.sportnews.adapter.FootballItaliaAdapter;
import edgar.yodgorbek.sportnews.internet.SportClient;
import edgar.yodgorbek.sportnews.model.Article;
import edgar.yodgorbek.sportnews.model.FootballItalia;
import edgar.yodgorbek.sportnews.model.SportInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FootballItaliaFragment extends Fragment {


    public List<Article> articleList = new ArrayList<Article>();
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private FootballItalia footballItalia;
    private FootballItaliaAdapter footballItaliaAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_footballitalia, container, false);

        ButterKnife.bind(this, view);
        SportInterface sportInterface = SportClient.getApiService();
        Call<FootballItalia> call = sportInterface.getFootballItaliaArticles();
        call.enqueue(new Callback<FootballItalia>() {
            @Override
            public void onResponse(Call<FootballItalia> call, Response<FootballItalia> response) {
                footballItalia = response.body();
                if (footballItalia != null && footballItalia.getFoxArticles() != null) {
                    articleList.addAll(footballItalia.getFoxArticles());
                }
                footballItaliaAdapter = new FootballItaliaAdapter(articleList, footballItalia);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(footballItaliaAdapter);
            }

            @Override
            public void onFailure(Call<FootballItalia> call, Throwable t) {

            }
        });


        return view;
    }

    public static void doFilter(String searchQuery) {

    }
}