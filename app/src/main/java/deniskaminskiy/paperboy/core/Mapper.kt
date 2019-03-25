package deniskaminskiy.paperboy.core

interface Mapper<F, T> {

    fun map(from: F): T

}