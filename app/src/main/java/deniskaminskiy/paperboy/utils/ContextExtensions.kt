package deniskaminskiy.paperboy.utils

import android.content.Context
import android.graphics.Typeface
import android.widget.Toast
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment

fun Context?.toast(message: String, length: Int = Toast.LENGTH_SHORT) =
    this?.run { Toast.makeText(this, message, length).show() }

fun Fragment?.toast(message: String, length: Int = Toast.LENGTH_SHORT) =
    this?.context?.toast(message, length)

fun Context?.compatFont(@FontRes id: Int): Typeface =
    this?.let { ResourcesCompat.getFont(it, id) } ?: Typeface.DEFAULT