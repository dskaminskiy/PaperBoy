package deniskaminskiy.paperboy.utils

import android.os.Bundle
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun Fragment.hideKeyboard() = activity?.run { this.hideKeyboard() }

fun Fragment.open(
    fragmentManager: FragmentManager?,
    @IdRes containerId: Int,
    tag: String,
    transition: Int = FragmentTransaction.TRANSIT_FRAGMENT_OPEN
) {
    fragmentManager?.beginTransaction()
        ?.setTransition(transition)
        ?.add(containerId, this, tag)
        ?.addToBackStack(tag)
        ?.commit()
}

fun Fragment.open(
    activity: FragmentActivity?,
    @IdRes containerId: Int,
    tag: String,
    transition: Int = FragmentTransaction.TRANSIT_FRAGMENT_OPEN
) {
    open(activity?.supportFragmentManager, containerId, tag, transition)
}

fun Fragment.open(
    fragmentManager: FragmentManager?,
    @IdRes containerId: Int,
    tag: String,
    transition: Int,
    @AnimRes enter: Int,
    @AnimRes exit: Int
) {
    fragmentManager?.beginTransaction()
        ?.setCustomAnimations(enter, exit, enter, exit)
        ?.add(containerId, this, tag)
        ?.addToBackStack(tag)
        ?.setTransition(transition)
        ?.commit()
}

inline fun <T : Fragment> T.args(builder: Bundle.() -> Unit): T {
    arguments = arguments ?: Bundle()
        .apply(builder)
    return this
}