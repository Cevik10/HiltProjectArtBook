package com.hakancevik.hiltprojectartbook.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hakancevik.hiltprojectartbook.model.Art

@Dao
interface ArtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art: Art)


    @Delete
    suspend fun deleteArt(art: Art)


    @Query("SELECT * FROM arts")
    fun observeArts(): LiveData<List<Art>>


}