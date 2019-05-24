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
import edgar.yodgorbek.sportnews.adapter.EspnAdapter;
import edgar.yodgorbek.sportnews.internet.SportClient;
import edgar.yodgorbek.sportnews.model.Article;
import edgar.yodgorbek.sportnews.model.Espn;
import edgar.yodgorbek.sportnews.model.SportInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ESPNFragment extends Fragment {

    public List<Article> articleList = new ArrayList<Article>();
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private Espn espn;
    private EspnAdapter espnAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_espn, container, false);

        ButterKnife.bind(this, view);
        SportInterface sportInterface = SportClient.getApiService();
        Call<Espn> call = sportInterface.getEspnArticles();
        call.enqueue(new Callback<Espn>() {
            @Override
            public void onResponse(Call<Espn> call, Response<Espn> response) {
                espn = response.body();

                if (espn != null && espn.getEspnArticles() != null) {
                    articleList.addAll(espn.getEspnArticles());
                }

                espnAdapter = new EspnAdapter(articleList, espn);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(espnAdapter);
            }

            @Override
            public void onFailure(Call<Espn> call, Throwable t) {

            }
        });
        return view;
    }


}