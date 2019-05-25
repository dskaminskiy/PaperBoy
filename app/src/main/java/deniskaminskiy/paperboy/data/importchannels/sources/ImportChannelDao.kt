package deniskaminskiy.paperboy.data.importchannels.sources

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import deniskaminskiy.paperboy.data.importchannels.ImportChannel

@Dao
interface ImportChannelDao {

    @Query("SELECT * FROM import_channels")
    fun getAll(): List<ImportChannel>

    @Insert
    fun insertAll(vararg channels: ImportChannel)

}