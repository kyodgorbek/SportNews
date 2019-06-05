package edgar.yodgorbek.sportnews.sportactivities;

import android.app.ProgressDialog;
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
import edgar.yodgorbek.sportnews.adapter.TalkSportAdapter;
import edgar.yodgorbek.sportnews.internet.SportClient;
import edgar.yodgorbek.sportnews.model.Article;
import edgar.yodgorbek.sportnews.model.Search;
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
    private SportInterface sportInterface;
    private Search search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bbcsport, container, false);
        ButterKnife.bind(this, view);
        sportInterface = SportClient.getApiService();
        talkSportAdapter = new TalkSportAdapter(articleList, talkSports);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(talkSportAdapter);
        fetchInitialArticles();


        return view;


    }

    private void fetchInitialArticles() {
        ProgressDialog progress = new ProgressDialog(getContext());
        progress.setMessage("Loading... ");
        progress.setIndeterminate(true);
        progress.show();
        Call<TalkSports> call = sportInterface.getSportArticles();
        call.enqueue(new Callback<TalkSports>() {
            @Override
            public void onResponse(Call<TalkSports> call, Response<TalkSports> response) {
                if (response.body() != null) {
                    talkSports = response.body();
                }
                if (talkSports.getSportArticles() != null) {
                    articleList.clear();
                    articleList.addAll(talkSports.getSportArticles());
                }
                talkSportAdapter.notifyDataSetChanged();
                progress.dismiss();

            }

            @Override
            public void onFailure(Call<TalkSports> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void doFilter(String searchQuery) {
        searchAPICall(searchQuery);
    }

    private void searchAPICall(String searchQuery) {
        ProgressDialog progress = new ProgressDialog(getContext());
        progress.setMessage("Searching... ");
        progress.setIndeterminate(true);
        progress.show();

        Call<Search> searchCall = sportInterface.getSearchViewArticles(searchQuery);
        searchCall.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                try {
                    search = response.body();

                    if (search != null && search.getArticles() != null) {
                        articleList.clear();
                        articleList.addAll(search.getArticles());
                    }
                    talkSportAdapter.notifyDataSetChanged();

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
