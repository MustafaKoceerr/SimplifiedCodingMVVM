package com.kocerlabs.simplifiedcodingmvvm.ui.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kocerlabs.simplifiedcodingmvvm.databinding.FragmentRegisterBinding
import com.kocerlabs.simplifiedcodingmvvm.data.network.AuthApi
import com.kocerlabs.simplifiedcodingmvvm.data.repository.AuthRepository
import com.kocerlabs.simplifiedcodingmvvm.ui.base.BaseFragment


class RegisterFragment : BaseFragment<AuthViewModel, FragmentRegisterBinding, AuthRepository>() {
    override fun getViewModel(): Class<AuthViewModel> = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): AuthRepository =
        AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)


}