package deniskaminskiy.paperboy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import deniskaminskiy.paperboy.data.importchannels.ImportChannel
import deniskaminskiy.paperboy.data.importchannels.sources.ImportChannelDao
import deniskaminskiy.paperboy.utils.DATABASE_NAME

@Database(entities = [ImportChannel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun importChannelsDao(): ImportChannelDao

    companion object {

        @Volatile
        private lateinit var instance: AppDatabase

        fun init(context: Context) {
            instance = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }

        fun getInstance(): AppDatabase = instance

    }

}