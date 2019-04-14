package deniskaminskiy.paperboy.utils.managers

interface ResourcesManager {

    val chooseChannelsYouWantImportSentence: String
    val chooseChannelsYouWantImportAccentWord: String

    val nowYouCanRemoveChannelsFromTelegramSentence: String
    val nowYouCanRemoveChannelsFromTelegramAccentWord: String

    fun youHaveChannels(count: Int): String

}