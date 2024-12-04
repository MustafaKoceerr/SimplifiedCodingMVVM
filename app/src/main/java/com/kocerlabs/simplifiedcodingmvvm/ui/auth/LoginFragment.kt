package com.kocerlabs.simplifiedcodingmvvm.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kocerlabs.simplifiedcodingmvvm.data.network.AuthApi
import com.kocerlabs.simplifiedcodingmvvm.data.network.Resource
import com.kocerlabs.simplifiedcodingmvvm.data.repository.AuthRepository
import com.kocerlabs.simplifiedcodingmvvm.databinding.FragmentLoginBinding
import com.kocerlabs.simplifiedcodingmvvm.enable
import com.kocerlabs.simplifiedcodingmvvm.showToast
import com.kocerlabs.simplifiedcodingmvvm.startNewActivity
import com.kocerlabs.simplifiedcodingmvvm.ui.base.BaseFragment
import com.kocerlabs.simplifiedcodingmvvm.ui.home.HomeActivity
import com.kocerlabs.simplifiedcodingmvvm.visible


class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {
    override fun getViewModel(): Class<AuthViewModel> = AuthViewModel::class.java

    val viewModel: AuthViewModel by viewModels { AuthViewModel.Factory }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): AuthRepository =
        AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()
        observers()

        with(binding) {
            progressbar.visible(false)
            buttonLogin.enable(false)


            editTextTextPassword.addTextChangedListener {
                val email = editTextTextEmailAddress.text.toString().trim()
                buttonLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
            }
        }

    }

    private fun setClickListeners() {
        with(binding) {
            buttonLogin.setOnClickListener {
                val email = binding.editTextTextEmailAddress.text.toString().trim()
                val password = binding.editTextTextPassword.text.toString().trim()
                // todo add input validations
                binding.progressbar.visible(true)
                buttonLogin.enable(false)

                viewModel.login(email, password)
            }
        }
    }

    private fun observers() {
        with(viewModel) {
            loginResponse.observe(viewLifecycleOwner, Observer {
                binding.progressbar.visible(false)
                binding.buttonLogin.enable(true)

                when (it) {
                    is Resource.Success -> {
                        requireContext().showToast("Basarili islem $it")
//                        lifecycleScope.launch {
                        // ONEMLI_NOT: Aslında burada direkt datastore işlemini yapmak iyi değil ama, şimdilik DI eklemediğimiz için şimdilik yapıyoruz.
                        // NOT2: Bunu repository'ye taşıyacağım ve view model aracılığıyla erişeceğim.
                        // artık viewModel'e taşıdım ve view model üzerinden çağıracağım, viewModelScope kullandım, yani artık
                        // lifecycleScope'a ihtiyacım yok.
                        it.value.accessToken?.let { token ->
                            viewModel.saveAuthToken(token)
                        }

                        requireActivity().startNewActivity(HomeActivity::class.java)
//                        }
                    }

                    is Resource.Failure<*> -> {
                        binding.progressbar.visible(false)
                        requireContext().showToast("Basarisiz islem $it")
                    }

                    Resource.Loading -> {
                        // do nothing
                    }
                }
            })


        }
    }
}