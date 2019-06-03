package deniskaminskiy.paperboy.data.importchannels

import deniskaminskiy.paperboy.core.Factory
import deniskaminskiy.paperboy.data.AppDatabase
import deniskaminskiy.paperboy.data.importchannels.sources.ImportChannelDao
import deniskaminskiy.paperboy.data.importchannels.sources.ImportChannelsCloudDataSource
import deniskaminskiy.paperboy.data.importchannels.sources.ImportChannelsCloudDataSourceImpl
import io.reactivex.Completable
import io.reactivex.Observable

interface ImportChannelsRepository {

    fun getFromCloud(): Observable<List<ImportChannel>>

    fun retain(channels: List<ImportChannel>): Completable

    fun getFromCache(): Observable<List<ImportChannel>>

    fun clearCache(): Completable

    fun subscribeChannels(ids: List<Long>): Completable

}

object ImportChannelsRepositoryFactory : Factory<ImportChannelsRepository> {

    private val instance by lazy { ImportChannelsRepositoryImpl() }

    override fun create(): ImportChannelsRepository = instance

}

class ImportChannelsRepositoryImpl(
    private val cloud: ImportChannelsCloudDataSource = ImportChannelsCloudDataSourceImpl(),
    private val cache: ImportChannelDao = AppDatabase.getInstance().importChannelsDao()
) : ImportChannelsRepository {

    private val mocks = listOf(
        ImportChannel(1, "Some Title", 3, false),
        ImportChannel(2, "Some Title", 2, false),
        ImportChannel(3, "Some Title", 1, false))

    override fun getFromCloud(): Observable<List<ImportChannel>> = cloud.channels()

    override fun getFromCache(): Observable<List<ImportChannel>> =  Observable.just(mocks) /*cache.getAll()*/

    override fun retain(channels: List<ImportChannel>): Completable = cache.insertAll(channels)

    override fun clearCache(): Completable = cache.deleteImportChannels()

    override fun subscribeChannels(ids: List<Long>): Completable = cloud.subscribeChannels(ids)
        .doOnComplete{ clearCache() }

}