package deniskaminskiy.paperboy.utils

import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.presentation.view.TopPopupPresentModel
import deniskaminskiy.paperboy.utils.icon.IconConstant
import deniskaminskiy.paperboy.utils.icon.IconFactory
import deniskaminskiy.paperboy.utils.managers.ResourcesProvider

/**
 * Should be initialized from Application file.
 */
object ErrorFactory {

    private var resources: ResourcesProvider? = null

    val errorUnknown: TopPopupPresentModel by lazy {
        TopPopupPresentModel(
            title = resources?.provideString(R.string.oops_something_happened) ?: "",
            subtitle = resources?.provideString(R.string.it_was_shit_sometimes_shit_happens) ?: "",
            icon = IconFactory.create(IconConstant.WARNING.constant),
            iconColor = resources?.provideColor(R.color.marlboroNew) ?: -1
        )
    }

    val errorNoNetworkConnection: TopPopupPresentModel by lazy {
        TopPopupPresentModel(
            title = resources?.provideString(R.string.no_network_connection) ?: "",
            subtitle = resources?.provideString(R.string.please_fix_it_and_try_again) ?: "",
            icon = IconFactory.create(IconConstant.WARNING.constant),
            iconColor = resources?.provideColor(R.color.marlboroNew) ?: -1
        )
    }

    fun init(resources: ResourcesProvider) {
        this.resources = resources
    }

}