package deniskaminskiy.paperboy.utils.icon


object IconFactory {

    private val drawableIconsDictionary = mutableListOf(
        IconConstant.ADD to DrawableConstantIcon(DrawableIconConstant.ADD),
        IconConstant.ARROW_BACK to DrawableConstantIcon(DrawableIconConstant.ARROW_BACK),
        IconConstant.TRASH to DrawableConstantIcon(DrawableIconConstant.TRASH)

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
    TRASH(2, "iconTrash")
}