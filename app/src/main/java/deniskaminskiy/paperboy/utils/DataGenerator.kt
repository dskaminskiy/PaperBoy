package deniskaminskiy.paperboy.utils

import deniskaminskiy.paperboy.data.channels.Channel

object DataGenerator {

    fun channels(): List<Channel> = listOf(
        Channel(0), Channel(1), Channel(2), Channel(3), Channel(4)
    )


}