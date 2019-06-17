package edgar.yodgorbek.yangiliklar.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SportInterface {


    @get:GET("/v2/top-headlines?sources=bbc-sport&apiKey=d03441ae1be44f9cad8c38a2fa6db215")
    val articles: Call<SportNews>

    @get:GET("/v2/top-headlines?sources=talksport&apiKey=d03441ae1be44f9cad8c38a2fa6db215")
    val sportArticles: Call<TalkSports>

    @get:GET("/v2/top-headlines?sources=fox-sports&apiKey=d03441ae1be44f9cad8c38a2fa6db215")
    val foxArticles: Call<FoxSports>

    @get:GET("/v2/top-headlines?sources=football-italia&apiKey=d03441ae1be44f9cad8c38a2fa6db215")
    val footballItaliaArticles: Call<FootballItalia>

    @get:GET("/v2/top-headlines?sources=espn&apiKey=d03441ae1be44f9cad8c38a2fa6db215")
    val espnArticles: Call<Espn>

    @GET("/v2/everything?apiKey=d03441ae1be44f9cad8c38a2fa6db215")
    fun getSearchViewArticles(@Query("q") q: String): Call<Search>


}
