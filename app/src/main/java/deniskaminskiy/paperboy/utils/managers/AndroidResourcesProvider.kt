package deniskaminskiy.paperboy.utils.managers

import android.content.Context
import android.graphics.Typeface
import androidx.fragment.app.Fragment
import deniskaminskiy.paperboy.utils.*

class AndroidResourcesProvider private constructor(
    private val contextDelegate: ContextDelegate
) : ResourcesProvider {

    companion object {

        fun create(fragment: Fragment) = AndroidResourcesProvider(FragmentContextDelegate(fragment))

        fun create(contextDelegate: ContextDelegate) = AndroidResourcesProvider(contextDelegate)

        fun create(context: Context) = AndroidResourcesProvider(ContextContextDelegate(context))

    }

    private val context: Context?
        get() = contextDelegate.getContext()

    constructor(context: Context) : this(ContextContextDelegate(context))

    override fun provideColor(id: Int): Int =
        context.compatColor(id)

    override fun provideString(id: Int): String =
        context?.getString(id) ?: ""

    //TODO: temp
    override fun provideString(id: Int, param: Int): String =
        context?.getString(id, param) ?: ""

    override fun provideTypeface(id: Int): Typeface =
        context.compatFont(id)

}