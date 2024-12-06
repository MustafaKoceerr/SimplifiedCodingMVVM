package com.kocerlabs.simplifiedcodingmvvm.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.kocerlabs.simplifiedcodingmvvm.data.UserPreferences
import com.kocerlabs.simplifiedcodingmvvm.data.network.model.RemoteDataSource
import com.kocerlabs.simplifiedcodingmvvm.data.repository.BaseRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// Data binding, view model'e ihtiyacımız var.
abstract class BaseFragment<VM : BaseViewModel, B : ViewBinding, R : BaseRepository> : Fragment() {
// Buradaki abstract fonksiyonları kullanarak, gerçek fragment'a geçince ihtiyacımız olan class'ları verecekler.

    protected lateinit var userPreferences: UserPreferences

    protected lateinit var binding: B
    protected val remoteDataSource = RemoteDataSource()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getFragmentBinding(inflater, container)
        userPreferences = UserPreferences(requireContext())

        lifecycleScope.launch {
            userPreferences.authToken.first()
        }

        return binding.root
    }

    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): B

    abstract fun getFragmentRepository(): R


}