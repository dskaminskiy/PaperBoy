package deniskaminskiy.paperboy.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import deniskaminskiy.paperboy.R
import kotlinx.android.synthetic.main.view_input_phone_number.view.*

class InputPhoneNumberView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_input_phone_number, this)

        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.LinearLayoutCompat, 0, 0)

        a.recycle()

        vNumber.requestFocus()
    }

    fun show(model: InputPhoneNumberPresentModel) {

    }

}

data class InputPhoneNumberPresentModel(
    val number: Int,
    val region: PhoneNumberRegion
)

enum class PhoneNumberRegion(
    val additionalNumber: String
) {
    RUSSIA("+7")
}