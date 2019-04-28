package deniskaminskiy.paperboy.data.auth.sources.mappers

import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.api.AuthResponseState
import deniskaminskiy.paperboy.data.api.AuthResponseState.*
import deniskaminskiy.paperboy.data.api.json.auth.AuthCodeResponse

class AuthCodeResponseToAuthResponseStateMapper : Mapper<AuthCodeResponse, AuthResponseState> {
    override fun map(from: AuthCodeResponse): AuthResponseState =
        when (from.state) {
            WAITING_FOR_PASSWORD.constant -> WAITING_FOR_PASSWORD
            AUTHORIZED.constant -> AUTHORIZED
            else -> ERROR
        }
}