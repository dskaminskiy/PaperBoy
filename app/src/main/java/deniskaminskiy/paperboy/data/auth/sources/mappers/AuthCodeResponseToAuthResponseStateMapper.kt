package deniskaminskiy.paperboy.data.auth.sources.mappers

import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.api.AuthResponseState
import deniskaminskiy.paperboy.data.api.ConstantToAuthResponseStateMapper
import deniskaminskiy.paperboy.data.api.json.auth.AuthCodeResponseJson

class AuthCodeResponseToAuthResponseStateMapper(
    private val stateMapper: Mapper<String?, AuthResponseState> = ConstantToAuthResponseStateMapper()
) : Mapper<AuthCodeResponseJson, AuthResponseState> {
    override fun map(from: AuthCodeResponseJson): AuthResponseState =
       stateMapper.map(from.state)
}