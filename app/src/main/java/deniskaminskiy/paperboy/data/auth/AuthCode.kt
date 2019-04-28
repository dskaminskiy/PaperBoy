package deniskaminskiy.paperboy.data.auth

data class AuthCode(
    val token: String,
    val error: String
)