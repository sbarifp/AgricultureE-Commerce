package com.sbarifp.pemasaranproduk.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sbarifp.pemasaranproduk.data.repository.auth.AuthRepository

class RegisterViewModel: ViewModel() {

    fun register(body: String) =
        AuthRepository.register(body).asLiveData()
}