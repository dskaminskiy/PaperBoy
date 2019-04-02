package deniskaminskiy.paperboy.presentation.intro.choose

import deniskaminskiy.paperboy.core.Interactor
import deniskaminskiy.paperboy.data.channel.ChannelImport
import io.reactivex.Observable

interface ChooseChannelsInteractor : Interactor {

    fun channelsImport(): Observable<List<ChannelImport>>

    fun channelsCount(): Int

}

class ChooseChannelsInteractorImpl(
    private val isChannelsFetched: Boolean
) : ChooseChannelsInteractor {

    private val channelsImportMocks = listOf(
        ChannelImport("1", "Mock 1"),
        ChannelImport("2", "Mock 2"),
        ChannelImport("3", "Mock 3")
    )

    // getFromCache
    // getFromCloud

    override fun channelsImport(): Observable<List<ChannelImport>> =
        Observable.just(channelsImportMocks)

    override fun channelsCount(): Int = -1

}