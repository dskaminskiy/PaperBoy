package deniskaminskiy.paperboy.data.importchannels.sources.mappers

import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.api.json.importchannels.ImportChannelResponseJson
import deniskaminskiy.paperboy.data.importchannels.ImportChannel

class ImportChannelFromResponseJsonMapper : Mapper<ImportChannelResponseJson, ImportChannel> {
    override fun map(from: ImportChannelResponseJson): ImportChannel =
        ImportChannel(from.id ?: -1L, from.title ?: "", from.order ?: -1L, false)
}