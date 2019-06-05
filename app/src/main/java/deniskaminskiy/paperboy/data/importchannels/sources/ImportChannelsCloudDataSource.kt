package deniskaminskiy.paperboy.data.importchannels.sources

import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.api.ApiService
import deniskaminskiy.paperboy.data.api.PaperboyApi
import deniskaminskiy.paperboy.data.api.json.importchannels.ImportChannelResponseJson
import deniskaminskiy.paperboy.data.importchannels.ImportChannel
import deniskaminskiy.paperboy.data.importchannels.sources.mappers.ImportChannelFromResponseJsonMapper
import io.reactivex.Completable
import io.reactivex.Observable

interface ImportChannelsCloudDataSource {

    fun channels(): Observable<List<ImportChannel>>

    fun subscribeChannels(ids: List<Long>): Completable

}

class ImportChannelsCloudDataSourceImpl(
    private val api: PaperboyApi = ApiService.paperboyApi,
    private val mapper: Mapper<ImportChannelResponseJson, ImportChannel> =
        ImportChannelFromResponseJsonMapper()
) : ImportChannelsCloudDataSource {

    override fun channels(): Observable<List<ImportChannel>> = api.importChannels()
        .map { it.data.map(mapper::map) }
        .toObservable()

    override fun subscribeChannels(ids: List<Long>): Completable = api.subscribeChannels(ids)

}