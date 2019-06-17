package edgar.yodgorbek.yangiliklar.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

import edgar.yodgorbek.yangiliklar.model.Article

@Dao
interface SportNewsDao {

    @get:Query("SELECT * FROM article")
    val articles: List<Article>


    @Insert
    fun insertAll(vararg article: Article)

    @Delete
    fun delete(vararg article: Article)

    @Update
    fun update(vararg article: Article)

}