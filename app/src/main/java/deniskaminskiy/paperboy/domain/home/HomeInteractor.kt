package deniskaminskiy.paperboy.domain.home

import deniskaminskiy.paperboy.core.Interactor
import deniskaminskiy.paperboy.data.channels.Channel
import deniskaminskiy.paperboy.utils.DataGenerator
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

interface HomeInteractor : Interactor {

    fun channels(): Observable<List<Channel>>

}

class HomeInteractorImpl : HomeInteractor {

    private val channelsSubject: BehaviorSubject<List<Channel>> =
        BehaviorSubject.createDefault(emptyList())

    override fun channels(): Observable<List<Channel>> = channelsSubject
        .delay(2, TimeUnit.SECONDS) // temp
        .also { channelsSubject.onNext(DataGenerator.channels()) }

}