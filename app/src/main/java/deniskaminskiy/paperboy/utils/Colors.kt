package deniskaminskiy.paperboy.utils

import android.content.Context
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import deniskaminskiy.paperboy.R

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

}

object ColorsFactory {

    fun create(fragment: Fragment) =
        AndroidColors(ContextDelegateFactory.create(fragment))

    fun create(context: Context) =
        AndroidColors(ContextDelegateFactory.create(context))

    fun create(activity: AppCompatActivity) =
        AndroidColors(ContextDelegateFactory.create(activity))

}

class AndroidColors(
    private val contextDelegate: ContextDelegate
) : Colors {

    override val primary: Int
        get() = c(R.color.colorPrimary)
    override val primaryDark: Int
        get() = c(R.color.colorPrimaryDark)
    override val accent: Int
        get() = c(R.color.colorAccent)

    override val paper: Int
        get() = c(R.color.paper)

    override val print10: Int
        get() = c(R.color.print10)
    override val print15: Int
        get() = c(R.color.print15)
    override val print20: Int
        get() = c(R.color.print20)
    override val print30: Int
        get() = c(R.color.print30)
    override val print40: Int
        get() = c(R.color.print40)
    override val print50: Int
        get() = c(R.color.print50)
    override val print60: Int
        get() = c(R.color.print60)
    override val print70: Int
        get() = c(R.color.print70)
    override val print80: Int
        get() = c(R.color.print80)
    override val print90: Int
        get() = c(R.color.print90)
    override val print100: Int
        get() = c(R.color.print100)

    override val marlboroNew: Int
        get() = c(R.color.marlboroNew)
    override val marlboroOld: Int
        get() = c(R.color.marlboroOld)


    private fun c(@ColorRes resId: Int): Int =
        contextDelegate.getContext().compatColor(resId)

}