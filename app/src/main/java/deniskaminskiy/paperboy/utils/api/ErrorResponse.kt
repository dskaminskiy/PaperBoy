package deniskaminskiy.paperboy.utils.api

data class ErrorResponse(
    val code: Int = 0,
    val message: String = "",
    val uuid: String = ""
)