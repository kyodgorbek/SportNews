package edgar.yodgorbek.yangiliklar.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Search {


    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("totalResults")
    @Expose
    var totalResults: Int? = null
    @SerializedName("articles")
    @Expose
    var articles: List<Article>? = null


}


