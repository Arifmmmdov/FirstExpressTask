package com.kaplan57.additemstorecyclerview.room.entity.dao

import androidx.room.*
import com.kaplan57.additemstorecyclerview.room.entity.entity.NoteEntity

@Dao
interface NoteDao {

    @Insert
    fun add(noteEntity: List<NoteEntity>)

    @Query("Select * from note_table")
    fun getNotes():List<NoteEntity>

    @Query("Update note_table set title=:title,note=:note where id=:position")
    fun update(position:Int,title:String,note:String)

    @Delete
    fun remove(entity:NoteEntity)
}