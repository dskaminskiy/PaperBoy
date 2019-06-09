package deniskaminskiy.paperboy.utils.icon


object IconFactory {

    private val drawableIconsDictionary = mutableListOf(
        IconConstant.ADD to DrawableConstantIcon(DrawableIconConstant.ADD),
        IconConstant.ARROW_BACK to DrawableConstantIcon(DrawableIconConstant.ARROW_BACK),
        IconConstant.TRASH to DrawableConstantIcon(DrawableIconConstant.TRASH),
        IconConstant.ARROW_FORWARD to DrawableConstantIcon(DrawableIconConstant.ARROW_FORWARD),
        IconConstant.WARNING to DrawableConstantIcon(DrawableIconConstant.WARNING),
        IconConstant.CIRCLE to DrawableConstantIcon(DrawableIconConstant.CIRCLE),
        IconConstant.BOOKMARK to DrawableConstantIcon(DrawableIconConstant.BOOKMARK)
    )

    /**
     * Возвращает drawable-иконку по указанному значению константы
     */
    @JvmStatic
    fun create(constant: String): DrawableConstantIcon? =
        drawableIconsDictionary.firstOrNull { it.first.constant == constant }?.second

    /**
     * Возвращает drawable-иконку по указанному значению id константы
     */
    @JvmStatic
    fun create(constantId: Int): DrawableConstantIcon? =
        drawableIconsDictionary.firstOrNull { it.first.id == constantId }?.second

}

enum class IconConstant(
    val id: Int,
    val constant: String
) {
    ADD(0, "iconAdd"),
    ARROW_BACK(1, "iconArrowBack"),
    TRASH(2, "iconTrash"),
    ARROW_FORWARD(3, "iconArrowForward"),
    WARNING(4, "iconWarning"),
    CIRCLE(5, "iconCircle"),
    BOOKMARK(6, "iconBookmark")
}