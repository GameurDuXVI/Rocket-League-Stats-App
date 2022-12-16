package be.arnaud.rocketleaguestats.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import be.arnaud.rocketleaguestats.db.converters.DateConverter
import java.util.*

@Entity(primaryKeys = ["identifier", "platform"])
@TypeConverters(DateConverter::class)
class SearchHistory(
    @ColumnInfo(name = "identifier") val identifier: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "platform") val platform: String,
    @ColumnInfo(name = "last_seen") val lastSeen: Date
)