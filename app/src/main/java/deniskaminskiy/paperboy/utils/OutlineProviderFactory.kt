package deniskaminskiy.paperboy.utils

import android.annotation.TargetApi
import android.graphics.Outline
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.FloatRange

object OutlineProviderFactory {

    private const val ELEVATION_ALPHA = 0.4f

    val outlineProviderDefault: ViewOutlineProvider by lazy {
        object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                val background = view.background
                if (background != null) {
                    background.getOutline(outline)
                    outline.alpha = ELEVATION_ALPHA
                } else {
                    outline.setRect(0, 0, view.width, view.height)
                    outline.alpha = 0.0f
                }
            }
        }
    }

}

/**
 * Возвращает кастомный outlineProvider
 *
 * @param cornerRadius      - задает закругление углов форме тени
 * @param scaleX            - позволяет масштабировть форму тени в пропорции по координате X
 * @param scaleY            - позволяет масштабировть форму тени в пропорции по координате Y
 * @param yShift            - добавляет смещение тени по вертикали
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
data class TweakableOutlineProvider(
    val cornerRadius: Float = 0f,
    var scaleX: Float = 1f,
    var scaleY: Float = 1f,
    var yShift: Int = 0
) : ViewOutlineProvider() {

    private val rect: Rect = Rect()

    override fun getOutline(view: View?, outline: Outline?) {
        view?.background?.copyBounds(rect)
        rect.scale(scaleX, scaleY)
        rect.offset(0, yShift)
        outline?.setRoundRect(rect, cornerRadius)
    }
}

private fun Rect.scale(
    @FloatRange(from = -1.0, to = 1.0) scaleX: Float,
    @FloatRange(from = -1.0, to = 1.0) scaleY: Float
) {
    val newWidth = width() * scaleX
    val newHeight = height() * scaleY
    val deltaX = (width() - newWidth) / 2
    val deltaY = (height() - newHeight) / 2

    set((left + deltaX).toInt(), (top + deltaY).toInt(), (right - deltaX).toInt(), (bottom - deltaY).toInt())
}