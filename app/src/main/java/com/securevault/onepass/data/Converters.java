package com.securevault.onepass.data;

import androidx.room.TypeConverter;

import java.time.LocalDate;

public class Converters {
    @TypeConverter
    public static String fromLocalDate(LocalDate date) {
        return date == null ? null : date.toString();
    }

    @TypeConverter
    public static LocalDate toLocalDate(String dateString) {
        return dateString == null ? null : LocalDate.parse(dateString);
    }
}
