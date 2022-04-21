package com.zlx.library_db.manager;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.zlx.library_db.converter.DateConverter;
import com.zlx.library_db.dao.SearchHistoryDao;
import com.zlx.library_db.entity.SearchHistoryEntity;


@TypeConverters(value = {DateConverter.class})
@Database(entities = {SearchHistoryEntity.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract SearchHistoryDao searchHistoryDao();
}

