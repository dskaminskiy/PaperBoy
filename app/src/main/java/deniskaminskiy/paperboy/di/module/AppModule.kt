package deniskaminskiy.paperboy.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import deniskaminskiy.paperboy.data.AppDatabase
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    internal fun provideAppDatabase(context: Context): AppDatabase =
            AppDatabase.getInstance(context)

}