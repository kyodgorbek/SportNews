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
import edgar.yodgorbek.sportnews.adapter.EspnAdapter;
import edgar.yodgorbek.sportnews.internet.SportClient;
import edgar.yodgorbek.sportnews.model.Article;
import edgar.yodgorbek.sportnews.model.Espn;
import edgar.yodgorbek.sportnews.model.Search;
import edgar.yodgorbek.sportnews.model.SportInterface;
import edgar.yodgorbek.sportnews.model.SportNews;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ESPNFragment extends Fragment {

    public List<Article> articleList = new ArrayList<Article>();
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private Espn espn;
    private EspnAdapter espnAdapter;
    private Search search;

    private SportInterface sportInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_espn, container, false);

        ButterKnife.bind(this, view);
        espnAdapter = new EspnAdapter(articleList, espn);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(espnAdapter);
        sportInterface = SportClient.getApiService();
        //  Call<Espn> call = sportInterface.getEspnArticles();
        fetchInitialArticles();

        return view;
    }

    private void fetchInitialArticles() {
        ProgressDialog progress = new ProgressDialog(getContext());
        progress.setMessage("Loading... ");
        progress.setIndeterminate(true);
        progress.show();
        Call<Espn> call = sportInterface.getEspnArticles();
        call.enqueue(new Callback<Espn>() {
            @Override
            public void onResponse(Call<Espn> call, Response<Espn> response) {
                if (response.body() != null) {
                    espn = response.body();
                }
                if (espn.getEspnArticles() != null) {
                    articleList.clear();
                    articleList.addAll(espn.getEspnArticles());
                }
                espnAdapter.notifyDataSetChanged();

                progress.dismiss();
            }

            @Override
            public void onFailure(Call<Espn> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }





    public  void doFilter(String searchQuery) {
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
                    espnAdapter.notifyDataSetChanged();

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
