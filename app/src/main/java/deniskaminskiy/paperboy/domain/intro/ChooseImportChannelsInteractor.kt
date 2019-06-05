package deniskaminskiy.paperboy.domain.intro

import deniskaminskiy.paperboy.core.Interactor
import deniskaminskiy.paperboy.data.importchannels.ImportChannel
import deniskaminskiy.paperboy.data.importchannels.ImportChannelsRepository
import deniskaminskiy.paperboy.data.importchannels.ImportChannelsRepositoryFactory
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface ChooseImportChannelsInteractor : Interactor {

    fun channels(): Observable<List<ImportChannel>>

    fun channelsCount(): Int

    fun changeCheckStatus(model: ImportChannel)

    fun subscribeChannels(): Completable

}

class ChooseImportChannelsInteractorImpl(
    private val repository: ImportChannelsRepository = ImportChannelsRepositoryFactory.create()
) : ChooseImportChannelsInteractor {

    private val importChannelsSubject: BehaviorSubject<List<ImportChannel>> =
        BehaviorSubject.createDefault(emptyList())

    private val subscribeChannelsIds: List<Long>
        get() = importChannelsSubject.value
            ?.filter { it.isChecked }
            ?.map { it.id }
            ?: emptyList()

    override fun channels(): Observable<List<ImportChannel>> =
        repository.getFromCache()
            .switchMap {
                importChannelsSubject.onNext(it)
                importChannelsSubject
            }

    override fun channelsCount(): Int = importChannelsSubject.value?.size ?: 0

    override fun changeCheckStatus(model: ImportChannel) {
        importChannelsSubject.onNext(
            importChannelsSubject.value
                ?.map {
                    if (it == model) {
                        it.copy(isChecked = !it.isChecked)
                    } else {
                        it
                    }
                } ?: emptyList()
        )
    }

    override fun subscribeChannels(): Completable = repository.subscribeChannels(subscribeChannelsIds)
        .doOnComplete { repository.clearCache() }

}