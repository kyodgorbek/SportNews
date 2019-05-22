package edgar.yodgorbek.sportnews.model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SportInterface {


    @GET("/v2/top-headlines?sources=bbc-sport&apiKey=d03441ae1be44f9cad8c38a2fa6db215")
    Call<SportNews> getArticles();

    @GET("/v2/top-headlines?sources=talksport&apiKey=d03441ae1be44f9cad8c38a2fa6db215")
    Call<TalkSports> getSportArticles();

    @GET("/v2/top-headlines?sources=fox-sports&apiKey=d03441ae1be44f9cad8c38a2fa6db215")
    Call<FoxSports> getFoxArticles();

    @GET("/v2/top-headlines?sources=football-italia&apiKey=d03441ae1be44f9cad8c38a2fa6db215")
    Call<FootballItalia> getFootballItaliaArticles();

    @GET("/v2/top-headlines?sources=espn&apiKey=d03441ae1be44f9cad8c38a2fa6db215")
    Call<Espn> getEspnArticles();


}
