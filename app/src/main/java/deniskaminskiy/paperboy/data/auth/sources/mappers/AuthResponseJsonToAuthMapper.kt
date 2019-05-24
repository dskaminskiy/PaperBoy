package deniskaminskiy.paperboy.data.auth.sources.mappers

import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.api.AuthResponseState
import deniskaminskiy.paperboy.data.api.ConstantToAuthResponseStateMapper
import deniskaminskiy.paperboy.data.api.json.auth.AuthResponseJson
import deniskaminskiy.paperboy.data.auth.Auth

class AuthResponseJsonToAuthMapper(
    private val stateMapper: Mapper<String?, AuthResponseState> = ConstantToAuthResponseStateMapper()
) : Mapper<AuthResponseJson, Auth> {
    override fun map(from: AuthResponseJson): Auth =
        Auth(from.token ?: "", stateMapper.map(from.state))
}