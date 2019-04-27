package deniskaminskiy.paperboy.data.api

enum class AuthResponseState(
    val constant: String
) {
    INITIALIZED("INITIALIZED"),
    WAITING_FOR_CODE("WAITING_FOR_CODE"),
    WAITING_FOR_PASSWORD("WAITING_FOR_RESPONSE"),
    AUTHORIZED("AUTHORIZED"),
    ERROR("ERROR"),
}