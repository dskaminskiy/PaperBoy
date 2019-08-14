package deniskaminskiy.paperboy.presentation.view

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.OnAnimationLifecycleListener
import deniskaminskiy.paperboy.utils.SimpleAnimatorListener
import deniskaminskiy.paperboy.utils.dp
import deniskaminskiy.paperboy.utils.icon.Icon
import deniskaminskiy.paperboy.utils.icon.IconFactory
import deniskaminskiy.paperboy.utils.icon.IconRendererFactory
import deniskaminskiy.paperboy.utils.icon.PaintedIcon
import deniskaminskiy.paperboy.utils.managers.AndroidResourcesProvider
import deniskaminskiy.paperboy.utils.managers.ResourcesProvider
import deniskaminskiy.paperboy.utils.view.gone
import deniskaminskiy.paperboy.utils.view.visible
import kotlinx.android.synthetic.main.view_top_popup.view.*

class TopPopupView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        const val CORNER_RADIUS = 12

        private const val DURATION_ANIM: Long = 350
        private const val DELAY_START_HIDING_ANIM: Long = 2000

        private const val PROPERTY_TRANSLATION_Y = "translationY"
        private const val PROPERTY_ALPHA = "alpha"

        private const val TRANSLATION_Y_OFFSET = -40f
        private const val TRANSLATION_Y_ORIGIN = 0f

        private const val ALPHA_VISIBLE = 1f
        private const val ALPHA_GONE = 0f
    }

    private val resources: ResourcesProvider by lazy { AndroidResourcesProvider.create(context) }

    private val dpCornerRadius = CORNER_RADIUS.dp(context).toFloat()

    private val defaultBackground by lazy {
        GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = dpCornerRadius
            setColor(resources.provideColor(R.color.print100))
        }
    }

    private val animShow = AnimatorSet().apply {
        playTogether(
            ObjectAnimator.ofFloat(
                this@TopPopupView,
                PROPERTY_TRANSLATION_Y,
                TRANSLATION_Y_OFFSET,
                TRANSLATION_Y_ORIGIN
            ),
            ObjectAnimator.ofFloat(this@TopPopupView, PROPERTY_ALPHA, ALPHA_GONE, ALPHA_VISIBLE)
        )
        interpolator = AccelerateDecelerateInterpolator()
        duration = DURATION_ANIM
    }

    private val animHide = AnimatorSet().apply {
        playTogether(
            ObjectAnimator.ofFloat(
                this@TopPopupView,
                PROPERTY_TRANSLATION_Y,
                TRANSLATION_Y_ORIGIN,
                TRANSLATION_Y_OFFSET
            ),
            ObjectAnimator.ofFloat(this@TopPopupView, PROPERTY_ALPHA, ALPHA_VISIBLE, ALPHA_GONE)
        )
        duration = DURATION_ANIM
        startDelay = DELAY_START_HIDING_ANIM
        interpolator = AccelerateDecelerateInterpolator()
    }

    private val animSet = AnimatorSet().apply {
        play(animShow).before(animHide)
    }

    var title: String
        get() = tvTitle.text.toString()
        set(value) {
            tvTitle.text = value
        }

    var titleColor: Int
        get() = tvTitle.currentTextColor
        set(value) {
            if (value != -1) {
                tvTitle.setTextColor(value)
            }
        }

    var subtitle: String
        get() = tvSubtitle.text.toString()
        set(value) {
            tvSubtitle.text = value
        }

    var subtitleColor: Int
        get() = tvSubtitle.currentTextColor
        set(value) {
            if (value != -1) {
                tvSubtitle.setTextColor(value)
            }
        }

    var icon: Icon? = null
        set(value) {
            field = value
            value?.let { IconRendererFactory.create(it) }
                ?.render(ivIcon)
        }

    init {
        View.inflate(context, R.layout.view_top_popup, this)

        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.TopPopupView, 0, 0)

        show(
            TopPopupPresModel(
                title = a.getString(R.styleable.TopPopupView_title) ?: "",
                titleColor = a.getColor(R.styleable.TopPopupView_titleColor, resources.provideColor(R.color.paper)),
                subtitle = a.getString(R.styleable.TopPopupView_subtitle) ?: "",
                subtitleColor = a.getColor(
                    R.styleable.TopPopupView_subtitleColor,
                    resources.provideColor(R.color.print40)
                ),
                icon = a.getInt(R.styleable.TopPopupView_iconConstant, -1)
                    .takeIf { it != -1 }?.let(IconFactory::create),
                iconColor = a.getInt(R.styleable.TopPopupView_iconColor, resources.provideColor(R.color.marlboroNew)),
                backgroundColor = a.getColor(
                    R.styleable.TopPopupView_backgroundColor,
                    resources.provideColor(R.color.print100)
                )
            )
        )

        a.recycle()
    }

    fun show(model: TopPopupPresModel) {
        title = model.title
        subtitle = model.subtitle
        titleColor = model.titleColor
        subtitleColor = model.subtitleColor
        icon = model.icon
        setIconColor(model.iconColor)
        setBackgroundColor(model.backgroundColor)
    }

    fun showWithAnimation(model: TopPopupPresModel, listener: OnAnimationLifecycleListener? = null) {
        show(model)

        if (animSet.isRunning || animSet.isStarted) {
            animSet.cancel()
        }

        animShow.removeAllListeners()
        animShow.addListener(object : SimpleAnimatorListener() {
            override fun onAnimationStart(animation: Animator) {
                listener?.onAnimationStart()
                visible()
                super.onAnimationStart(animation)
            }

            override fun onAnimationEnd(animation: Animator) {
                listener?.onAnimationEnd()
                super.onAnimationEnd(animation)
            }
        })

        animHide.removeAllListeners()
        animHide.addListener(object : SimpleAnimatorListener() {
            override fun onAnimationStart(animation: Animator) {
                listener?.onAnimationStart()
                super.onAnimationStart(animation)
            }

            override fun onAnimationEnd(animation: Animator) {
                listener?.onAnimationEnd()
                gone()
                super.onAnimationEnd(animation)
            }
        })

        animSet.start()
    }


    fun setIconColor(@ColorInt color: Int) {
        if (color != -1) {
            icon?.let {
                if (it is PaintedIcon) icon = it.toColor(context, color)
            }
        }
    }

    override fun setBackgroundColor(color: Int) {
        if (color != -1) {
            background = defaultBackground.apply {
                setColor(color)
            }
        }
    }

}

data class TopPopupPresModel(
    val title: String,
    val subtitle: String = "",
    @ColorInt val titleColor: Int = -1,
    @ColorInt val subtitleColor: Int = -1,
    val icon: Icon? = null,
    @ColorInt val iconColor: Int = -1,
    @ColorInt val backgroundColor: Int = -1
)