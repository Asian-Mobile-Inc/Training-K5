package issues13.ex1.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserModel(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    @ColumnInfo(name = "user_name") val name: String?,
    @ColumnInfo(name = "age") val age: String?,
    var countNumber: Int? = 0
)
