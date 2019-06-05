package deniskaminskiy.paperboy.data.api.json.auth

data class AuthPasswordRequestJson(
    val token: String,
    val password: String
)