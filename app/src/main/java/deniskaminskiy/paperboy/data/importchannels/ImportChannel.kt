package deniskaminskiy.paperboy.data.importchannels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "import_channels")
data class ImportChannel(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "is_checked") val isChecked: Boolean
)