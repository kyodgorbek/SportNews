package edgar.yodgorbek.sportnews.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import edgar.yodgorbek.sportnews.model.Article;

@Dao
public interface SportNewsDao {

    @Query("SELECT * FROM article")
    List<Article> getArticles();


    @Insert
    void insertAll(Article... article);
    @Delete
    void delete(Article... article);

    @Update
    void update(Article... article);

}