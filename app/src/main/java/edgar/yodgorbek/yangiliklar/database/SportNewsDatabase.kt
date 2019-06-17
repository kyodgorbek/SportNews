package edgar.yodgorbek.yangiliklar.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

import edgar.yodgorbek.yangiliklar.model.Article
import edgar.yodgorbek.yangiliklar.model.Source

@TypeConverters(SourceTypeConverter::class)
@Database(entities = [Article::class, Source::class], version = 1, exportSchema = false)
abstract class SportNewsDatabase : RoomDatabase() {
    abstract fun sportNewsDao(): SportNewsDao
}