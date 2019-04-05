package deniskaminskiy.paperboy.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Context?.toast(message: String, length: Int = Toast.LENGTH_SHORT) =
    this?.run { Toast.makeText(this, message, length).show() }

fun Fragment?.toast(message: String, length: Int = Toast.LENGTH_SHORT) =
    this?.context?.toast(message, length)