package com.nopyjf.projectscbexplorer.database

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "token")
data class TokenEntity(
    @PrimaryKey var id: Int,
    @NonNull var accessToken: String,
    @NonNull var refreshToken: String

)