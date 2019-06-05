package deniskaminskiy.paperboy.utils

import deniskaminskiy.paperboy.presentation.view.TopPopupPresentModel
import deniskaminskiy.paperboy.utils.icon.IconConstant
import deniskaminskiy.paperboy.utils.icon.IconFactory
import deniskaminskiy.paperboy.utils.managers.ResourcesManager

/**
 * Should be initialized from Application file.
 */
object ErrorFactory {

    private var resources: ResourcesManager? = null

    val unknownError: TopPopupPresentModel by lazy {
        TopPopupPresentModel(
            title = resources?.strings?.somethingHappened ?: "",
            subtitle = resources?.strings?.sometimesShitHappens ?: "",
            icon = IconFactory.create(IconConstant.WARNING.constant),
            iconColor = resources?.colors?.marlboroNew ?: -1
        )
    }

    fun init(resources: ResourcesManager) {
        this.resources = resources
    }

}