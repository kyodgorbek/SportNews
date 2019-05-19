package edgar.yodgorbek.sportnews.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import edgar.yodgorbek.sportnews.model.Article;
import edgar.yodgorbek.sportnews.model.Source;

@TypeConverters(SourceTypeConverter.class)
@Database(entities = {Article.class, Source.class}, version = 1, exportSchema = false)
public  abstract class SportNewsDatabase extends RoomDatabase {
    public  abstract SportNewsDao sportNewsDao();
}