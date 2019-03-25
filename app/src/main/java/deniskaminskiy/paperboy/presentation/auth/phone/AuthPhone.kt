package deniskaminskiy.paperboy.presentation.auth.phone

data class AuthPhone(
    val region: Int,
    val phone: Long
) {
    companion object {
        val EMPTY = AuthPhone(-1, -1)
    }
}