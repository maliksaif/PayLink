package com.pay.link.presentation.ui.fragments.auth.login

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.pay.link.R
import com.pay.link.databinding.FragmentLoginBinding
import com.pay.link.presentation.ui.fragments.auth.login.LoginFragmentDirections.Companion.toHome
import com.pay.link.presentation.ui.fragments.auth.login.LoginViewEffect.Loading
import com.pay.link.presentation.ui.fragments.auth.login.LoginViewEffect.NavigateToHome
import com.pay.link.presentation.ui.fragments.auth.login.LoginViewEffect.ShowErrorSnackBar
import com.pay.link.presentation.ui.fragments.auth.login.LoginViewEffect.ShowSuccessSnackBar
import com.pay.link.presentation.ui.fragments.auth.login.LoginViewEvent.OnEmailChanged
import com.pay.link.presentation.ui.fragments.auth.login.LoginViewEvent.OnLoginClicked
import com.pay.link.presentation.ui.fragments.auth.login.LoginViewEvent.OnPasswordChanged
import com.pay.link.presentation.utils.base.BaseFragment
import com.pay.link.presentation.utils.CustomProgressDialog
import com.pay.link.presentation.utils.SnackBarManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    FragmentLoginBinding::inflate,
    LoginViewModel::class
) {

    @Inject
    lateinit var progressDialog: CustomProgressDialog
    @Inject
    lateinit var snackBar: SnackBarManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        observeViewState()
        observeViewEffect()
    }


    private fun setListeners() {

        binding.emailEt.addTextChangedListener { viewModel.onEvent(OnEmailChanged(it.toString())) }
        binding.passwordEt.addTextChangedListener { viewModel.onEvent(OnPasswordChanged(it.toString())) }

        binding.loginActionButton.setOnClickListener { viewModel.onEvent(OnLoginClicked) }

    }

    private fun observeViewState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { viewState ->

                    binding.loginActionButton.isEnabled = viewState.isButtonEnabled
                }
            }
        }
    }


    private fun observeViewEffect() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewEffect.collect { viewEffect ->
                    when (viewEffect) {
                        is Loading -> progressDialog.show(
                            viewEffect.isLoading,
                            getString(R.string.please_wait)
                        )

                        NavigateToHome -> findNavController().navigate(toHome())
                        is ShowErrorSnackBar -> snackBar.showErrorSnackBar(
                            binding.root,
                            viewEffect.message
                        )

                        is ShowSuccessSnackBar -> snackBar.showSuccessSnackBar(
                            binding.root,
                            viewEffect.message
                        )
                    }
                }
            }
        }

    }
}