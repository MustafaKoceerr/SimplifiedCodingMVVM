package com.kocerlabs.simplifiedcodingmvvm.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kocerlabs.simplifiedcodingmvvm.data.network.Resource
import com.kocerlabs.simplifiedcodingmvvm.data.network.UserApi
import com.kocerlabs.simplifiedcodingmvvm.data.network.model.LoginResponseSecond
import com.kocerlabs.simplifiedcodingmvvm.data.repository.UserRepository
import com.kocerlabs.simplifiedcodingmvvm.databinding.FragmentHomeBinding
import com.kocerlabs.simplifiedcodingmvvm.ui.base.BaseFragment
import com.kocerlabs.simplifiedcodingmvvm.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, UserRepository>() {
    val viewModel: HomeViewModel by viewModels { HomeViewModel.Factory }


    override fun getViewModel(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {

        val token = runBlocking { userPreferences.authToken.first() }
        // Base Fragment'ta first() ile bir kere topladık, runblocking içinde o değere direkt erişecek. First ile bir kere toplarsan,
        // bir daha erişirken toplamayacak.
        return UserRepository(remoteDataSource.buildApi(UserApi::class.java, token))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressbar.visible(false)

        binding.buttonLogout.setOnClickListener {
            viewModel.getUser()
        }

        with(viewModel) {
            getUser()

            user.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is Resource.Success -> {
                        // update UI
                        binding.progressbar.visible(false)
                        updateUI(it.value)
                    }

                    is Resource.Failure<*> -> {
                        binding.progressbar.visible(false)

                    }

                    Resource.Loading -> {
                        binding.progressbar.visible(true)
                    }
                }
            })
        }

    }

    private fun updateUI(loginResponseSecond: LoginResponseSecond) {
        with(binding) {
            textViewEmail.text = loginResponseSecond.email
            textViewName.text =
                "${loginResponseSecond.firstName} ${loginResponseSecond.maidenName} ${loginResponseSecond.lastName}"
            textViewId.text = loginResponseSecond.id.toString()
        }

    }
}