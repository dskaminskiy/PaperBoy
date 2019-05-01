package deniskaminskiy.paperboy.utils.managers

interface ResourcesManager {

    val colors: ResourcesManager.Colors
    val strings: ResourcesManager.Strings

    interface Colors {

        val primary: Int
        val primaryDark: Int
        val accent: Int

        val paper: Int

        val print10: Int
        val print15: Int
        val print20: Int
        val print30: Int
        val print40: Int
        val print50: Int
        val print60: Int
        val print70: Int
        val print80: Int
        val print90: Int
        val print100: Int

        val marlboroNew: Int
        val marlboroOld: Int

        val admiral: Int

    }

    interface Strings {

        val chooseChannelsYouWantImportSentence: String
        val chooseChannelsYouWantImportAccentWord: String

        val nowYouCanRemoveChannelsFromTelegramSentence: String
        val nowYouCanRemoveChannelsFromTelegramAccentWord: String

        fun youHaveChannels(count: Int): String

        val somethingHappened: String
        val sometimesShitHappens: String

    }

}