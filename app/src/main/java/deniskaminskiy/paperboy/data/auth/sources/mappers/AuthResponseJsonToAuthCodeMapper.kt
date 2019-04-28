package deniskaminskiy.paperboy.data.auth.sources.mappers

import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.api.json.auth.AuthResponseJson
import deniskaminskiy.paperboy.data.auth.Auth

class AuthResponseJsonToAuthCodeMapper : Mapper<AuthResponseJson, Auth> {
    override fun map(from: AuthResponseJson): Auth =
            Auth(from.token ?: "", "")
}