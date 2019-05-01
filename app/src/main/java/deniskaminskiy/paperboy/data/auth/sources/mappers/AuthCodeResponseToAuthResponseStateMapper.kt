package deniskaminskiy.paperboy.data.auth.sources.mappers

import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.api.AuthResponseState
import deniskaminskiy.paperboy.data.api.ConstantToAuthResponseStateMapper
import deniskaminskiy.paperboy.data.api.json.auth.AuthCodeResponse

class AuthCodeResponseToAuthResponseStateMapper(
    private val stateMapper: Mapper<String?, AuthResponseState> = ConstantToAuthResponseStateMapper()
) : Mapper<AuthCodeResponse, AuthResponseState> {
    override fun map(from: AuthCodeResponse): AuthResponseState =
       stateMapper.map(from.state)
}