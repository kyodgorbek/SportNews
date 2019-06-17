package edgar.yodgorbek.yangiliklar.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Espn {

    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("totalResults")
    @Expose
    var totalResults: Int? = null
    @SerializedName("articles")
    @Expose
    var espnArticles: List<Article>? = null
        private set

    fun setArticles(articles: List<Article>) {
        this.espnArticles = articles
    }
}




