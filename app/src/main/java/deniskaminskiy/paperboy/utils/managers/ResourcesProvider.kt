package deniskaminskiy.paperboy.utils.managers

import android.graphics.Typeface
import androidx.annotation.ColorRes
import androidx.annotation.StringRes

interface ResourcesProvider {

    fun provideColor(@ColorRes id: Int): Int

    fun provideString(@StringRes id: Int): String

    fun provideString(@StringRes id: Int, vararg formatArgs: Any): String

    fun provideTypeface(id: Int): Typeface

}