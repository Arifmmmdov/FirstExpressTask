package com.kaplan57.additemstorecyclerview.room.entity.database

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.kaplan57.additemstorecyclerview.room.entity.dao.NoteDao
import com.kaplan57.additemstorecyclerview.room.entity.entity.NoteEntity

@Database(entities = [NoteEntity::class],version = 1)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun userDao():NoteDao
}