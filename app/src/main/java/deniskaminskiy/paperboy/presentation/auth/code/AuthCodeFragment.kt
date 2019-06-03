package deniskaminskiy.paperboy.presentation.auth.code

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.fragment.app.FragmentManager
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BaseFragment
import deniskaminskiy.paperboy.presentation.auth.security.AuthSecurityCodeFragment
import deniskaminskiy.paperboy.presentation.intro.choose.ChooseImportChannelsFragment
import deniskaminskiy.paperboy.utils.hideKeyboard
import deniskaminskiy.paperboy.utils.managers.AndroidResourcesManager
import deniskaminskiy.paperboy.utils.open
import deniskaminskiy.paperboy.utils.replace
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

        presenter = AuthCodePresenter(
            view = this,
            resources = AndroidResourcesManager.create(this)
        ).also { presenter ->
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
        vLoading.gone()

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
            for (i in 1..5) {
                updateInputText(i, "")
            }
        }

        textLength.let {
            when (it) {
                0 -> ivFirst
                1 -> ivSecond
                2 -> ivThird
                3 -> ivFourth
                else -> ivFifth
            }.requestFocus()
        }

        presenter?.onInputsUpdateFinish()
    }

    override fun showLoading() {
        vLoading.visible()
    }

    override fun hideLoading() {
        vLoading.gone()
    }

    private fun updateInputText(
        @IntRange(from = 1, to = 5) inputViewNumber: Int,
        newText: String
    ) {

        when (inputViewNumber) {
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

    override fun showImportChannels(isChannelsFetched: Boolean) {
        hideKeyboard()
        ChooseImportChannelsFragment.newInstance(isChannelsFetched)
            .replace(activity, R.id.vgContent, ChooseImportChannelsFragment.TAG)
    }

    override fun showSmsSended() {
        toast(getString(R.string.code_was_sent_to_you_by_sms))
    }

    override fun close() {
        activity?.supportFragmentManager?.popBackStack()
    }

}