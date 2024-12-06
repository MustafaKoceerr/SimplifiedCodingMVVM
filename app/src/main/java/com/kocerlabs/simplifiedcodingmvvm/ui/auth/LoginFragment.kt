package com.kocerlabs.simplifiedcodingmvvm.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.kocerlabs.simplifiedcodingmvvm.data.network.Resource
import com.kocerlabs.simplifiedcodingmvvm.databinding.FragmentLoginBinding
import com.kocerlabs.simplifiedcodingmvvm.enable
import com.kocerlabs.simplifiedcodingmvvm.handleApiError
import com.kocerlabs.simplifiedcodingmvvm.startNewActivity
import com.kocerlabs.simplifiedcodingmvvm.ui.base.BaseFragment
import com.kocerlabs.simplifiedcodingmvvm.ui.home.HomeActivity
import com.kocerlabs.simplifiedcodingmvvm.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: AuthViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        observers()

        with(binding) {
            buttonLogin.enable(false)
            progressbar.visible(false)
            editTextTextPassword.addTextChangedListener {
                val email = editTextTextEmailAddress.text.toString().trim()
                buttonLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
            }
        }

    }


    private fun login() {
        with(binding) {
            val email = editTextTextEmailAddress.text.toString().trim()
            val password = editTextTextPassword.text.toString().trim()
            // todo add input validations
            buttonLogin.enable(false)
            viewModel.login(email, password)
        }
    }


    private fun setClickListeners() {
        binding.buttonLogin.setOnClickListener { login() }
    }


    private fun observers() {
        with(viewModel) {
            loginResponse.observe(viewLifecycleOwner, Observer {
                binding.buttonLogin.enable(true)
                binding.progressbar.visible(false)

                when (it) {
                    is Resource.Success -> {
                        lifecycleScope.launch {
                            it.value.accessToken?.let { token ->
                                viewModel.saveAuthToken(token)
                            }

                            requireActivity().startNewActivity(HomeActivity::class.java)
                        }
                    }

                    is Resource.Failure<*> -> {
                        handleApiError(it, { login() })
                    }

                    Resource.Loading -> {
                        binding.progressbar.visible(true)
                    }
                }
            })
        }
    }


}