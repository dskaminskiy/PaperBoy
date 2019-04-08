package deniskaminskiy.paperboy.presentation.intro.choose

import deniskaminskiy.paperboy.core.Interactor
import deniskaminskiy.paperboy.data.channel.ChannelImport
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

interface ChooseChannelsInteractor : Interactor {

    fun channelsImport(): Observable<List<ChannelImport>>

    fun channelsCount(): Int

    fun changeCheckStatus(model: ChannelImport)

}

class ChooseChannelsInteractorImpl(
    private val isChannelsFetched: Boolean
) : ChooseChannelsInteractor {

    private val channelsImportSubject: BehaviorSubject<List<ChannelImport>> =
        BehaviorSubject.createDefault(emptyList())

    private val channelsImportMocks = listOf(
        ChannelImport("1", "PSD | Дизайн пространство", false),
        ChannelImport("2", "Now How Channel", false),
        ChannelImport("3", "Канал Ильи Дзенски", false),
        ChannelImport("4", "Разговоры об управлении", false),
        ChannelImport("5", "Mood board picker", false),
        ChannelImport("6", "Дизайн-афиша", false),
        ChannelImport("7", "Миша Петрик", false),
        ChannelImport("8", "Хулиномика", false)
    )

    init {
        channelsImportSubject.onNext(channelsImportMocks)
    }

    // getFromCache
    // getFromCloud

    override fun channelsImport(): Observable<List<ChannelImport>> =
        channelsImportSubject

    override fun channelsCount(): Int = channelsImportSubject.value?.size ?: 0

    override fun changeCheckStatus(model: ChannelImport) {
        channelsImportSubject.onNext(
            channelsImportSubject.value
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