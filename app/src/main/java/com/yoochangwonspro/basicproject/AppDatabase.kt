package com.yoochangwonspro.basicproject

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yoochangwonspro.basicproject.dao.HistoryDao
import com.yoochangwonspro.basicproject.model.History

@Database(entities = [History::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}