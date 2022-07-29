package com.kaplan57.additemstorecyclerview.room.entity.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class NoteEntity(
    @PrimaryKey
    val id:Int,
    @ColumnInfo
    var title:String,
    @ColumnInfo
    val note:String
)