package edgar.yodgorbek.yangiliklar.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "article", foreignKeys = [ForeignKey(entity = Source::class, parentColumns = ["id"], childColumns = ["source_id"])])
class Article {
    @PrimaryKey
    @SerializedName("source")
    @Expose
    lateinit var source: Source
    var source_id: Int = 0
    @SerializedName("author")
    @Expose
    @ColumnInfo(name = "author")
    var author: String? = null
    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    var title: String? = null
    @SerializedName("description")
    @Expose
    @ColumnInfo(name = "description")
    var description: String? = null
    @SerializedName("url")
    @Expose
    @ColumnInfo(name = "url")
    var url: String? = null
    @SerializedName("urlToImage")
    @Expose
    @ColumnInfo(name = "urlToImage")
    var urlToImage: String? = null
    @SerializedName("publishedAt")
    @Expose
    @ColumnInfo(name = "publishedAt")
    var publishedAt: String? = null
    @SerializedName("content")
    @Expose
    @ColumnInfo(name = "content")
    var content: String? = null

}
