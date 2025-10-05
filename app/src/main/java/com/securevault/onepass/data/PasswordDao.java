package com.securevault.onepass.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PasswordDao {
    @Query("SELECT * FROM passwords ORDER BY password_name ASC")
    List<PasswordItem> retrieveRecord();

    @Insert
    void insertRecord(PasswordItem passwordItem);

    @Update
    void updateRecord(PasswordItem passwordItem);
}