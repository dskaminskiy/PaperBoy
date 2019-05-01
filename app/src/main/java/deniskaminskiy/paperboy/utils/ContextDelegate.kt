package deniskaminskiy.paperboy.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

interface ContextDelegate {

    fun getContext(): Context?

}

object ContextDelegateFactory {

    fun create(fragment: Fragment): ContextDelegate =
        FragmentContextDelegate(fragment)

    fun create(context: Context): ContextDelegate =
        ContextContextDelegate(context)

    fun create(activity: AppCompatActivity): ContextDelegate =
        ActivityContextDelegate(activity)

}

class FragmentContextDelegate(fragment: Fragment) : ContextDelegate {

    private val fragmentRef = WeakReference(fragment)

    override fun getContext(): Context? = fragmentRef.get()?.activity

}

class ContextContextDelegate(context: Context) : ContextDelegate {

    private val contextRef = WeakReference<Context>(context)

    override fun getContext(): Context? = contextRef.get()

}

class ActivityContextDelegate(activity: AppCompatActivity) : ContextDelegate {

    private val activityRef = WeakReference(activity)

    override fun getContext(): Context? = activityRef.get()

}