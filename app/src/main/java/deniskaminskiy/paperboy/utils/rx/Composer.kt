package deniskaminskiy.paperboy.utils.rx

import io.reactivex.*

interface Composer {

    fun <T> observable(): ObservableTransformer<T, T>

    fun completable(): CompletableTransformer

    fun <T> single(): SingleTransformer<T, T>

    fun <T> maybe(): MaybeTransformer<T, T>

    fun <T> flowable(): FlowableTransformer<T, T>

}