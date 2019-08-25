package deniskaminskiy.paperboy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import deniskaminskiy.paperboy.data.importchannel.ImportChannel
import deniskaminskiy.paperboy.data.importchannel.sources.ImportChannelDao
import deniskaminskiy.paperboy.utils.Constants.DATABASE_NAME

@Database(entities = [ImportChannel::class], version = 1, exportSchema = false)
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