package com.nopyjf.projectscbexplorer.database

import androidx.room.*

@Dao
interface TokenDAO {

    @Query("select * from token where id = :id")
    fun getToken(id: Int): TokenEntity?

    @Insert
    fun insert(user: TokenEntity)

    @Delete
    fun delete(user: TokenEntity)

    @Update
    fun update(user: TokenEntity)
}