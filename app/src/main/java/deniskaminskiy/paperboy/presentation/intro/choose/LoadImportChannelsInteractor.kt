package deniskaminskiy.paperboy.presentation.intro.choose

import deniskaminskiy.paperboy.core.Interactor
import deniskaminskiy.paperboy.data.importchannels.ImportChannelsRepository
import deniskaminskiy.paperboy.data.importchannels.ImportChannelsRepositoryFactory
import io.reactivex.Completable

interface LoadImportChannelsInteractor : Interactor {

    fun load(): Completable

}

class LoadImportChannelsInteractorImpl(
    private val repository: ImportChannelsRepository = ImportChannelsRepositoryFactory.create()
) : LoadImportChannelsInteractor {

    override fun load(): Completable = Completable.complete()

}