package deniskaminskiy.paperboy.presentation.intro.choose

import deniskaminskiy.paperboy.core.Interactor
import deniskaminskiy.paperboy.data.importchannels.ImportChannel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface ChooseImportChannelsInteractor : Interactor {

    fun channels(): Observable<List<ImportChannel>>

    fun channelsCount(): Int

    fun changeCheckStatus(model: ImportChannel)

}

class ChooseImportChannelsInteractorImpl(
    private val isChannelsFetched: Boolean
) : ChooseImportChannelsInteractor {

    private val importChannelsSubject: BehaviorSubject<List<ImportChannel>> =
        BehaviorSubject.createDefault(emptyList())

    private val channelsImportMocks = listOf(
        ImportChannel("1", "PSD | Дизайн пространство", false),
        ImportChannel("2", "Now How Channel", false),
        ImportChannel("3", "Канал Ильи Дзенски", false),
        ImportChannel("4", "Разговоры об управлении", false),
        ImportChannel("5", "Mood board picker", false),
        ImportChannel("6", "Дизайн-афиша", false),
        ImportChannel("7", "Миша Петрик", false),
        ImportChannel("8", "Хулиномика", false)
    )

    init {
        importChannelsSubject.onNext(channelsImportMocks)
    }

    // getFromCache
    // getFromCloud

    override fun channels(): Observable<List<ImportChannel>> =
        importChannelsSubject

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

}