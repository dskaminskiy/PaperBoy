package deniskaminskiy.paperboy.data.auth.sources.mappers

import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.api.AuthResponseState
import deniskaminskiy.paperboy.data.api.AuthResponseState.*
import deniskaminskiy.paperboy.data.api.json.auth.AuthPasswordResponse

class AuthPasswordResponseToAuthResponseStateMapper: Mapper<AuthPasswordResponse, AuthResponseState> {
    override fun map(from: AuthPasswordResponse): AuthResponseState =
        when (from.state) {
            AUTHORIZED.constant -> AUTHORIZED
            else -> ERROR
        }
}