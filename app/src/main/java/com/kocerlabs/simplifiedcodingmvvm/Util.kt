package com.kocerlabs.simplifiedcodingmvvm


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast

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

