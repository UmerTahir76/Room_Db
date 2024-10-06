package com.example.roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPerson(personEntity: PersonEntity)

    @Update
    suspend fun updatePerson(personEntity: PersonEntity)

    @Delete
    suspend fun deletePerson(personEntity: PersonEntity)

    @Query("SELECT * FROM person_table")
    fun getAllData() : Flow<List<PersonEntity>>

    @Query("SELECT * FROM person_table WHERE person_name LIKE '%' || :query || '%' or person_age LIKE '%' || :query || '%' or person_city LIKE '%' || :query || '%' ")
    fun searchPerson(query : String) : Flow<List<PersonEntity>>
}