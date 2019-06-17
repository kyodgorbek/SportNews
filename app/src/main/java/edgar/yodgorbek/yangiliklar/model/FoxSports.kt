package edgar.yodgorbek.yangiliklar.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FoxSports {

    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("totalResults")
    @Expose
    var totalResults: Int? = null
    @SerializedName("articles")
    @Expose
    var foxArticles: List<Article>? = null
        private set

    fun setSportArticles(articles: List<Article>) {
        this.foxArticles = articles
    }
}
