package deniskaminskiy.paperboy.data.api.json.auth

data class AuthPasswordRequest(
    val token: String,
    val password: String
)