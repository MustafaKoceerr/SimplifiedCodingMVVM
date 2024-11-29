package com.kocerlabs.simplifiedcodingmvvm.network

import okhttp3.ResponseBody

sealed class Resource<out T> { // subClass'lardan return type olarak covariant almak için sealed class'ta da <out T> tanımladım.
    // out T'yi sadece Resource sınıfında tanımlayarak, türetilmiş sınıflar (Success, Error, vb.) bu parametreyi otomatik olarak miras alır.
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure<out T>(val isNetworkError: Boolean, val errorCode: Int?,
        val errorBody:ResponseBody?) : Resource<T>()

}


/*
// Covariant sınıf
class Box<out T>(val value: T) {
    fun get(): T = value
}

// Fonksiyon, Box<Animal> alır ama Box<Dog> veya Box<Cat> de alabilir
fun printAnimalSound(box: Box<Animal>) {
    println(box.get().makeSound())
}

fun main() {
    val dogBox: Box<Dog> = Box(Dog())  // Dog türü Box<Dog> içinde
    val catBox: Box<Cat> = Box(Cat())  // Cat türü Box<Cat> içinde

    // Covariant olduğu için Box<Dog> bir Box<Animal> yerine kullanılabilir
    printAnimalSound(dogBox)  // Çıktı: Woof
    printAnimalSound(catBox)  // Çıktı: Meow
}
 */
/** Evet, doğru! Covariant bir türde, T ve T'nin alt türlerini kullanabilirsiniz. */