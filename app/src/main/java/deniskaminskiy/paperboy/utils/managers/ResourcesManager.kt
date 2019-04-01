package deniskaminskiy.paperboy.utils.managers

interface ResourcesManager {

    val chooseChannelsYouWantImport: String

    fun youHaveChannels(count: Int): String

}