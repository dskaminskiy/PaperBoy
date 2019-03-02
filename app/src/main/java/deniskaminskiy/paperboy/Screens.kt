package deniskaminskiy.paperboy

import android.content.Context
import android.content.Intent
import deniskaminskiy.paperboy.presentation.main.MainActivity
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object MainScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context): Intent =
            Intent(context, MainActivity::class.java)
    }

}