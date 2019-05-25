package deniskaminskiy.paperboy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import deniskaminskiy.paperboy.data.importchannels.ImportChannel
import deniskaminskiy.paperboy.data.importchannels.sources.ImportChannelDao
import deniskaminskiy.paperboy.utils.DATABASE_NAME

@Database(entities = arrayOf(ImportChannel::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun importChannelsDao(): ImportChannelDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }

    }

}