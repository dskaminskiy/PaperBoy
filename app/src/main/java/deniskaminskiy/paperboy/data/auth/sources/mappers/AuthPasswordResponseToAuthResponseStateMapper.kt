package deniskaminskiy.paperboy.data.auth.sources.mappers

import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.api.AuthResponseState
import deniskaminskiy.paperboy.data.api.AuthResponseState.AUTHORIZED
import deniskaminskiy.paperboy.data.api.AuthResponseState.ERROR
import deniskaminskiy.paperboy.data.api.json.auth.AuthPasswordResponseJson

class AuthPasswordResponseToAuthResponseStateMapper: Mapper<AuthPasswordResponseJson, AuthResponseState> {
    override fun map(from: AuthPasswordResponseJson): AuthResponseState =
        when (from.state) {
            AUTHORIZED.constant -> AUTHORIZED
            else -> ERROR
        }
}