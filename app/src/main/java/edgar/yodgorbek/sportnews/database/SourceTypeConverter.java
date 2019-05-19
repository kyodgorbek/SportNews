package edgar.yodgorbek.sportnews.database;

import android.arch.persistence.room.TypeConverter;

import edgar.yodgorbek.sportnews.model.Source;

public class  SourceTypeConverter {
    @TypeConverter
    public static String ConvertSource(Source source){
        return source == null ? null : source.toString();
    }

    @TypeConverter
    public static Source ConvertSource(String source){
        return source == null ? null : new Source(source);
    }
}
