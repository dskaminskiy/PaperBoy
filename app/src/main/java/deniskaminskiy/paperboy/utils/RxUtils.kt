package deniskaminskiy.paperboy.utils

import io.reactivex.disposables.Disposable

fun Disposable?.disposeIfNotNull() {
    if (this != null && !isDisposed) {
        dispose()
    }
}