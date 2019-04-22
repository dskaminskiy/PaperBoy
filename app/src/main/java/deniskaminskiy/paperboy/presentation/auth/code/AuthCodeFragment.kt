package deniskaminskiy.paperboy.presentation.auth.code

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationSet
import androidx.annotation.IntRange
import androidx.fragment.app.FragmentManager
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BaseFragment
import deniskaminskiy.paperboy.presentation.auth.security.AuthSecurityCodeFragment
import deniskaminskiy.paperboy.presentation.intro.choose.ChooseChannelsFragment
import deniskaminskiy.paperboy.utils.SimpleAnimatorListener
import deniskaminskiy.paperboy.utils.hideKeyboard
import deniskaminskiy.paperboy.utils.open
import deniskaminskiy.paperboy.utils.toast
import deniskaminskiy.paperboy.utils.view.gone
import deniskaminskiy.paperboy.utils.view.visible
import kotlinx.android.synthetic.main.fragment_auth_code.*

class AuthCodeFragment : BaseFragment<AuthCodePresenter, AuthCodeView>(), AuthCodeView {

    companion object {
        const val TAG = "AuthCodeFragment"

        fun newInstance() = AuthCodeFragment()
    }

    private val onBackStackChangedListener = FragmentManager.OnBackStackChangedListener {
        if (activity?.supportFragmentManager?.fragments?.last() == this) {
            ivFirst.post {
                ivFirst.requestFocus()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_auth_code, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = AuthCodePresenter(this).also { presenter ->
            listOf(ivFirst, ivSecond, ivThird, ivFourth, ivFifth).apply {
                forEach { it.onTextChanged = presenter::onPassCodeChanged }
                forEach { it.onBackspacePressedWithEmptyText = presenter::onBackspacePressedWithEmptyText }
            }

            vBack.setOnClickListener { presenter.onBackClick() }
            vSendSms.setOnClickListener { presenter.onSendSmsClick() }
        }

        activity?.supportFragmentManager?.addOnBackStackChangedListener(onBackStackChangedListener)
    }

    override fun onDestroyView() {
        activity?.supportFragmentManager?.removeOnBackStackChangedListener(onBackStackChangedListener)
        super.onDestroyView()
    }

    override fun show(model: AuthCodePresentModel) {
        val textLength = model.code.length

        if (textLength >= 1) {
            // Обновляем текст в засеченых инпутах
            for (i in 1..textLength) {
                updateInputText(i, model.code[i - 1].toString())
            }
            // Чистим оставшиеся инпуты
            for (i in (textLength + 1)..5) {
                updateInputText(i, "")
            }
        } else {
            ivFifth.text = ""
            ivFourth.text = ""
            ivThird.text = ""
            ivSecond.text = ""
            ivFirst.text = ""
        }

        textLength.let {
            when(it) {
                0 -> ivFirst
                1 -> ivSecond
                2 -> ivThird
                3 -> ivFourth
                else -> ivFifth
            }.requestFocus()
        }

        presenter?.onInputsUpdateFinish()
    }

    private fun updateInputText(@IntRange(from = 1, to = 5) inputViewNumber: Int,
                                newText: String) {

        when(inputViewNumber) {
            1 -> ivFirst.text = newText
            2 -> ivSecond.text = newText
            3 -> ivThird.text = newText
            4 -> ivFourth.text = newText
            else -> ivFifth.text = newText
        }
    }

    override fun showAuthSecurityCode() {
        AuthSecurityCodeFragment.newInstance()
            .open(activity, R.id.vgContent, AuthSecurityCodeFragment.TAG)
    }

    override fun showImportChannels() {
        hideKeyboard()
        ChooseChannelsFragment.newInstance(true)
            .open(activity, R.id.vgContent, ChooseChannelsFragment.TAG)
    }

    override fun showError() {
        val showAnim = AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(vTopPopup, "translationY", -80f, 0f),
                ObjectAnimator.ofFloat(vTopPopup, "alpha", 0f, 1f)
            )
            interpolator = AccelerateDecelerateInterpolator()
            duration = 350
        }

        val hideAnim = AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(vTopPopup, "translationY", 0f, -80f),
                ObjectAnimator.ofFloat(vTopPopup, "alpha", 1f, 0f)
            )
            duration = 350
            startDelay = 1000
            interpolator = AccelerateDecelerateInterpolator()
        }

        showAnim.addListener(object : SimpleAnimatorListener() {
            override fun onAnimationStart(animation: Animator) {
                presenter?.onAnimationStart()
                vTopPopup.visible()
                super.onAnimationStart(animation)
            }

            override fun onAnimationEnd(animation: Animator) {
                presenter?.onAnimationEnd()
                super.onAnimationEnd(animation)
            }
        })

        hideAnim.addListener(object : SimpleAnimatorListener() {
            override fun onAnimationStart(animation: Animator) {
                presenter?.onAnimationStart()
                super.onAnimationStart(animation)
            }

            override fun onAnimationEnd(animation: Animator) {
                presenter?.onAnimationEnd()
                vTopPopup.gone()
                super.onAnimationEnd(animation)
            }
        })

        val anim = AnimatorSet().apply {
            play(showAnim).before(hideAnim)
        }.start()

    }

    override fun showSmsSended() {
        toast(getString(R.string.code_was_sent_to_you_by_sms))
    }

    override fun close() {
        activity?.supportFragmentManager?.popBackStack()
    }

}