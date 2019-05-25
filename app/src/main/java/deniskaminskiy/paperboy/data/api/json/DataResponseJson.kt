package deniskaminskiy.paperboy.data.api.json

data class DataResponseJson<T>(
    val code: Int? = null,
    val data: List<T> = emptyList(),
    val message: String? = null
) {

    /**
     * Нужно для запросов, требующих лишь одного конкретного экземпляра информации, который будет
     * приходить первым и единственным элементом списка.
     */
    val soleData: T?
        get() = data.firstOrNull()

}