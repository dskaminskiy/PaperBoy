package deniskaminskiy.paperboy.presentation.auth.code

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BaseFragment
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
            ivFirst.onTextChanged = presenter::onPassCodeChanged
            ivSecond.onTextChanged = presenter::onPassCodeChanged
            ivThird.onTextChanged = presenter::onPassCodeChanged
            ivFourth.onTextChanged = presenter::onPassCodeChanged
            ivFifth.onTextChanged = presenter::onPassCodeChanged

            vBack.setOnClickListener { presenter.onBackClick() }
        }

    }

    override fun show(model: AuthCodePresentModel) {
        //InputView -> TODO

        val textLength = model.code.length

        textLength.let {
            when(it) {
                0 -> ivFirst
                1 -> ivSecond
                2 -> ivThird
                3 -> ivFourth
                else -> ivFifth
            }.requestFocus()
        }

        if (textLength >= 1) {
            for (i in 1..textLength) updateInputText(i, model.code[i - 1].toString())
        }
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

    override fun close() {
        activity?.supportFragmentManager?.popBackStack()
    }

}