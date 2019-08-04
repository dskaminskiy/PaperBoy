package deniskaminskiy.paperboy.domain.intro

import deniskaminskiy.paperboy.core.Interactor
import deniskaminskiy.paperboy.data.importchannels.ImportChannel
import deniskaminskiy.paperboy.data.importchannels.ImportChannelsRepository
import deniskaminskiy.paperboy.data.importchannels.ImportChannelsRepositoryFactory
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface ChooseImportChannelsInteractor : Interactor {

    fun channels(isFromCache: Boolean): Observable<List<ImportChannel>>

    fun channelsCount(): Int

    fun changeCheckStatus(model: ImportChannel)

    fun subscribeChannels(): Completable

}

class ChooseImportChannelsInteractorImpl(
    private val repository: ImportChannelsRepository = ImportChannelsRepositoryFactory.create()
) : ChooseImportChannelsInteractor {

    private val subjectImportChannels: BehaviorSubject<List<ImportChannel>> =
        BehaviorSubject.createDefault(emptyList())

    private val subscribeChannelsIds: List<Long>
        get() = subjectImportChannels.value
            ?.filter { it.isChecked }
            ?.map { it.id }
            ?: emptyList()

    override fun channels(isFromCache: Boolean): Observable<List<ImportChannel>> =
        repository.let {
            if (isFromCache) {
                it.getFromCache()
            } else {
                it.getFromCloud()
            }
        }
            .map(::copyCheckStatuses)
            .switchMap {
                subjectImportChannels.onNext(it)
                subjectImportChannels
            }

    private fun copyCheckStatuses(newChannels: List<ImportChannel>): List<ImportChannel> {
        val previousChannels = subjectImportChannels.value ?: emptyList()

        return newChannels.map { newChannel ->
            newChannel.copy(isChecked = previousChannels.firstOrNull { it.id == newChannel.id }?.isChecked == true)
        }
    }


    override fun channelsCount(): Int = subjectImportChannels.value?.size ?: 0

    override fun changeCheckStatus(model: ImportChannel) {
        subjectImportChannels.onNext(
            subjectImportChannels.value
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