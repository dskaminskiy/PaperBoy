package deniskaminskiy.paperboy.core

interface Factory<T> {

    fun create(): T

}