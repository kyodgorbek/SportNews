package edgar.yodgorbek.yangiliklar.database

import android.arch.persistence.room.TypeConverter

import edgar.yodgorbek.yangiliklar.model.Source

object SourceTypeConverter {
    @TypeConverter
    fun ConvertSource(source: Source?): String? {
        return source?.toString()
    }

    @TypeConverter
    fun ConvertSource(source: String?): Source? {
        return if (source == null) null else Source(source)
    }
}
