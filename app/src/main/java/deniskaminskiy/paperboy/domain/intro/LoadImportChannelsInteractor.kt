package deniskaminskiy.paperboy.domain.intro

import deniskaminskiy.paperboy.core.Interactor
import deniskaminskiy.paperboy.data.importchannels.ImportChannelsRepository
import deniskaminskiy.paperboy.data.importchannels.ImportChannelsRepositoryFactory
import io.reactivex.Completable

interface LoadImportChannelsInteractor : Interactor {

    fun loadAndCache(): Completable

}

class LoadImportChannelsInteractorImpl(
    private val repository: ImportChannelsRepository = ImportChannelsRepositoryFactory.create()
) : LoadImportChannelsInteractor {

    override fun loadAndCache(): Completable = repository.getFromCloud()
        .flatMapCompletable{
            Completable.complete()
        }

}