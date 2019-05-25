package deniskaminskiy.paperboy.data.importchannels

import deniskaminskiy.paperboy.core.Factory
import deniskaminskiy.paperboy.data.importchannels.sources.ImportChannelsCloudDataSource
import deniskaminskiy.paperboy.data.importchannels.sources.ImportChannelsCloudDataSourceImpl
import io.reactivex.Completable

interface ImportChannelsRepository {

    fun load(): Completable

}

object ImportChannelsRepositoryFactory: Factory<ImportChannelsRepository> {

    private val instance by lazy { ImportChannelsRepositoryImpl() }

    override fun create(): ImportChannelsRepository = instance

}

class ImportChannelsRepositoryImpl(
    private val cloud: ImportChannelsCloudDataSource = ImportChannelsCloudDataSourceImpl()
): ImportChannelsRepository {

    override fun load(): Completable = Completable.complete()

}