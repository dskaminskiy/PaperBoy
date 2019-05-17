package deniskaminskiy.paperboy.data.api

import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.api.AuthResponseState.*

enum class AuthResponseState(
    val constant: String
) {
    INITIALIZED("INITIALIZED"),
    WAITING_FOR_CODE("WAITING_FOR_CODE"),
    WAITING_FOR_PASSWORD("WAITING_FOR_PASSWORD"),
    AUTHORIZED("AUTHORIZED"),
    ERROR("ERROR"),
}

class ConstantToAuthResponseStateMapper : Mapper<String?, AuthResponseState> {
    override fun map(from: String?): AuthResponseState = when (from) {
        INITIALIZED.constant -> INITIALIZED
        AUTHORIZED.constant -> AUTHORIZED
        WAITING_FOR_CODE.constant -> WAITING_FOR_CODE
        WAITING_FOR_PASSWORD.constant -> WAITING_FOR_PASSWORD
        else -> ERROR
    }
}

fun AuthResponseState.ifInitialized(function: () -> Unit) {
    if (this == INITIALIZED) function.invoke()
}

fun AuthResponseState.ifWaitingForCode(function: () -> Unit) {
    if (this == WAITING_FOR_CODE) function.invoke()
}

fun AuthResponseState.ifWaitingForPassword(function: () -> Unit) {
    if (this == WAITING_FOR_PASSWORD) function.invoke()
}

fun AuthResponseState.ifAuthorized(function: () -> Unit) {
    if (this == AUTHORIZED) function.invoke()
}

fun AuthResponseState.ifError(function: () -> Unit) {
    if (this == ERROR) function.invoke()
}