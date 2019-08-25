package deniskaminskiy.paperboy.data.importchannel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "import_channels")
data class ImportChannel(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "order") val order: Long,
    @ColumnInfo(name = "is_checked") val isChecked: Boolean
)