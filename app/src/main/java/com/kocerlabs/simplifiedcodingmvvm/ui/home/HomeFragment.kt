package com.kocerlabs.simplifiedcodingmvvm.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kocerlabs.simplifiedcodingmvvm.data.network.Resource
import com.kocerlabs.simplifiedcodingmvvm.data.network.model.LoginResponseSecond
import com.kocerlabs.simplifiedcodingmvvm.databinding.FragmentHomeBinding
import com.kocerlabs.simplifiedcodingmvvm.ui.base.BaseFragment
import com.kocerlabs.simplifiedcodingmvvm.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val TAG = "HomeFragment"
    private val viewModel: HomeViewModel by viewModels()


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)


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