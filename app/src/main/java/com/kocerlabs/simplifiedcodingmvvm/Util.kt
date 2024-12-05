package com.kocerlabs.simplifiedcodingmvvm


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.kocerlabs.simplifiedcodingmvvm.data.network.Resource
import com.kocerlabs.simplifiedcodingmvvm.ui.auth.LoginFragment
import com.kocerlabs.simplifiedcodingmvvm.ui.base.BaseFragment

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}


fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        // kullanıcı giriş yaptıktan sonra activity'mi yeni bir activitymiş gibi başaltıyorum ki,
        // kullanıcı back butonuna bastığında login ekranına geri dönemesin.
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    // login buttonunu disabled olarak göstermek için
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}


fun Fragment.handleApiError(
    failure: Resource.Failure<*>,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> {
            requireView().snackbar("Please check your internet connection", retry)
        }

        failure.errorCode == 401 -> {
            if (this is LoginFragment) {
                requireView().snackbar("You've entered incorrect email or password", retry)
            } else {
                (this as BaseFragment<*, *, *>).logout()
            }
        }

        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbar(error)
        }
    }
}


fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    // snackbar'ın action button'u vardır.
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    action?.let { retry ->
        snackbar.setAction("Retry") {
            retry()
        }
    }
    snackbar.show()
}