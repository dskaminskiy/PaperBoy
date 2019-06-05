package deniskaminskiy.paperboy.utils.rx

import io.reactivex.disposables.Disposable

fun Disposable?.disposeIfNotNull() {
    if (this != null && !isDisposed) {
        dispose()
    }
}