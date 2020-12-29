package com.example.androidkotlin.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity /*(tableName = "useraccounts", indices = arrayOf(Index(value = ["email", "password"], unique = true)))*/
class User : Serializable
{
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "username")
    var username: String? = null

    @ColumnInfo(name = "email")
    var email: String? = null

    @ColumnInfo(name = "password")
    var password: String? = null

    @ColumnInfo(name = "confirmPassword")
    var confirmPassword: String? = null

}