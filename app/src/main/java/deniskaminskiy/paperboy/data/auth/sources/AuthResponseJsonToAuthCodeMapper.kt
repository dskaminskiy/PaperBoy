package deniskaminskiy.paperboy.data.auth.sources

import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.api.json.auth.AuthResponseJson
import deniskaminskiy.paperboy.data.auth.AuthCode

class AuthResponseJsonToAuthCodeMapper : Mapper<AuthResponseJson, AuthCode> {
    override fun map(from: AuthResponseJson): AuthCode =
            AuthCode(from.token ?: "", "")
}