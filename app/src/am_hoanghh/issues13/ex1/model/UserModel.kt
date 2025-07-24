package issues13.ex1.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import issues13.ex1.model.UserModel.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    @ColumnInfo(name = "user_name")
    val name: String?,
    @ColumnInfo(name = "age")
    val age: String?,
    var countNumber: Int? = 0
) {
    companion object {
        const val TABLE_NAME = "user"
    }
}
