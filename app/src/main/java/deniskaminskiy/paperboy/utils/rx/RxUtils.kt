package deniskaminskiy.paperboy.utils.rx

import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

fun Disposable?.disposeIfNotNull() {
    if (this != null && !isDisposed) {
        dispose()
    }
}

fun PublishSubject<Unit>.emit() {
    this.onNext(Unit)
}