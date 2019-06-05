package deniskaminskiy.paperboy.data.auth

import deniskaminskiy.paperboy.data.api.AuthResponseState

data class Auth(
    val token: String,
    val state: AuthResponseState
) {
    companion object {
        val EMPTY = Auth("", AuthResponseState.ERROR)
    }
}