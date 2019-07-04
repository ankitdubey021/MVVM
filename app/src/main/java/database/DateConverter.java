package database;

import java.util.Date;

import androidx.room.TypeConverter;

public class DateConverter {

    @TypeConverter
    public Date toDate(Long timeStamp){
        return (timeStamp==null)?null:new Date(timeStamp);
    }

    @TypeConverter
    public Long toTimestamp(Date date){
        return date==null? null : date.getTime();
    }

}
