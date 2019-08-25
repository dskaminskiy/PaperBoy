package deniskaminskiy.paperboy.data.importchannel.sources

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import deniskaminskiy.paperboy.data.importchannel.ImportChannel
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface ImportChannelDao {

    @Query("SELECT * FROM import_channels")
    fun getAll(): Observable<List<ImportChannel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(channels: List<ImportChannel>): Completable

    @Query("DELETE FROM import_channels")
    fun deleteImportChannels(): Completable

}