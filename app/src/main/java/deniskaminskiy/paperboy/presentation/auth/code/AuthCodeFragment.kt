package deniskaminskiy.paperboy.presentation.auth.code

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BaseFragment
import deniskaminskiy.paperboy.presentation.intro.choose.ChooseChannelsFragment
import deniskaminskiy.paperboy.utils.hideKeyboard
import deniskaminskiy.paperboy.utils.open
import kotlinx.android.synthetic.main.fragment_auth_code.*

class AuthCodeFragment : BaseFragment<AuthCodePresenter, AuthCodeView>(), AuthCodeView {

    companion object {
        const val TAG = "AuthCodeFragment"

        fun newInstance() = AuthCodeFragment()
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
        }

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

    override fun showImportChannels() {
        hideKeyboard()
        ChooseChannelsFragment.newInstance(true)
            .open(activity, R.id.vgContent, ChooseChannelsFragment.TAG)
    }

    override fun close() {
        activity?.supportFragmentManager?.popBackStack()
    }

}