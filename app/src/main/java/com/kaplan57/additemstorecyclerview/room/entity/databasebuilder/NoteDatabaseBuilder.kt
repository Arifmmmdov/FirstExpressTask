package com.kaplan57.additemstorecyclerview.room.entity.databasebuilder

import android.content.Context
import androidx.room.Room
import com.kaplan57.additemstorecyclerview.room.entity.database.NoteDatabase


class NoteDatabaseBuilder {
    companion object{
        private var instance:NoteDatabase? = null

        fun getDatabaseBuilder(context: Context): NoteDatabase {
            if(instance == null){
                instance = Room.databaseBuilder(context, NoteDatabase::class.java, "note_table").build()
            }
            return instance as NoteDatabase
        }
    }
}