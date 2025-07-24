package com.example.asian.issue13.ex1.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(@ColumnInfo(name = "user_name") var name: String, @ColumnInfo(name = "age") var age: Int) {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "user_id") var id: Int = 0;
    @Ignore var position: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (name != other.name) return false
        if (age != other.age) return false
        if (id != other.id) return false
        if (position != other.position) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + age
        result = 31 * result + id
        result = 31 * result + position
        return result
    }
}
