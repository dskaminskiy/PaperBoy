package deniskaminskiy.paperboy.data.api.json.auth

data class AuthCodeRequest(
    val token: String,
    val code: Int
)