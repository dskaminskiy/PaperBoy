package deniskaminskiy.paperboy.data.importchannel.sources.mappers

import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.api.json.importchannels.ImportChannelResponseJson
import deniskaminskiy.paperboy.data.importchannel.ImportChannel

class ImportChannelFromResponseJsonMapper : Mapper<ImportChannelResponseJson, ImportChannel> {
    override fun map(from: ImportChannelResponseJson): ImportChannel =
        ImportChannel(from.id ?: -1L, from.title ?: "", from.order?.toLong() ?: -1L, false)
}