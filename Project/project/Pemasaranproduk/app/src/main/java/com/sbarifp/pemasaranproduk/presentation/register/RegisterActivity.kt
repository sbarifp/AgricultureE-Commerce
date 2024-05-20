package com.sbarifp.pemasaranproduk.presentation.register

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.sbarifp.pemasaranproduk.R
import com.sbarifp.pemasaranproduk.data.model.Resource
import com.sbarifp.pemasaranproduk.data.model.auth.RegisterRequest
import com.sbarifp.pemasaranproduk.databinding.ActivityRegisterBinding
import com.sbarifp.pemasaranproduk.utils.showDialogError
import com.sbarifp.pemasaranproduk.utils.showDialogLoading
import com.sbarifp.pemasaranproduk.utils.showDialogNotification
import com.sbarifp.pemasaranproduk.utils.showDialogSuccess
import com.sbarifp.pemasaranproduk.utils.toast

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var dialogLoading: AlertDialog
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialogLoading = showDialogLoading(this)
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        initActionBar()
        onAction()
    }

    private fun onAction() {
        binding.btnRegister.setOnClickListener {
            val name = binding.etFullNameRegister.text.toString().trim()
            val email = binding.etEmailRegister.text.toString().trim()
            val phone = binding.etPhoneRegister.text.toString().trim()
            val pass = binding.etPasswordRegister.text.toString().trim()
            val confirmPass = binding.etConfirmPasswordRegister.text.toString().trim()

            if (checkValid(name, email, phone, pass, confirmPass)){
                registerToServer(name, email, phone, pass)
            }
        }

        binding.tbRegister.setNavigationOnClickListener { onBackPressed() }
    }

    private fun registerToServer(name: String, email: String, phone: String, pass: String) {
        val registerRequest = RegisterRequest(
            name = name,
            email = email,
            password = pass,
            phone = phone
        )

        val body = Gson().toJson(registerRequest)

        registerViewModel.register(body).observe(this){ state ->
            when(state){
                is Resource.Empty -> {
                    dialogLoading.dismiss()
                    showDialogNotification(this, "EMPTY")
                }
                is Resource.Error -> {
                    dialogLoading.dismiss()
                    val errorMessage = state.errorMessage
                    showDialogError(this, errorMessage)
                }
                is Resource.Loading -> {
                    dialogLoading.show()
                }
                is Resource.Success -> {
                    dialogLoading.dismiss()
                    val data = state.data

                    val dialogSuccess = showDialogSuccess(this, data.message)
                    dialogSuccess.show()

                    Handler(Looper.getMainLooper())
                        .postDelayed({
                            dialogSuccess.dismiss()
                            finish()
                        }, 1200)
                }
            }
        }
    }

    private fun checkValid(
        name: String,
        email: String,
        phone: String,
        pass: String,
        confirmPass: String
    ): Boolean {
        return when {
            name.isEmpty() -> {
                binding.textInputFullNameRegister.error = getString(R.string.field_name)
                binding.textInputFullNameRegister.requestFocus()
                false
            }

            email.isEmpty() -> {
                binding.textInputFullNameRegister.error = null

                binding.textInputFullNameRegister.error = getString(R.string.field_name)
                binding.textInputFullNameRegister.requestFocus()
                false
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.textInputEmailRegister.error = getString(R.string.valid_email)
                binding.textInputEmailRegister.requestFocus()
                false
            }

            phone.isEmpty() -> {
                binding.textInputEmailRegister.error = null

                binding.textInputPhoneRegister.error = getString(R.string.field_phone)
                binding.textInputPhoneRegister.requestFocus()
                false
            }

            !Patterns.PHONE.matcher(phone).matches() -> {
                binding.textInputPhoneRegister.error = getString(R.string.valid_phone)
                binding.textInputPhoneRegister.requestFocus()
                false
            }

            pass.isEmpty() -> {
                binding.textInputPhoneRegister.error = null

                binding.textInputPasswordRegister.error = getString(R.string.field_password)
                binding.textInputPasswordRegister.requestFocus()
                false
            }

            confirmPass.isEmpty() -> {
                binding.textInputPasswordRegister.error = null

                binding.textInputConfirmPasswordRegister.error = getString(R.string.field_password)
                binding.textInputConfirmPasswordRegister.requestFocus()
                false
            }
            pass != confirmPass -> {
                binding.textInputPasswordRegister.error = getString(R.string.field_didn_t_match)
                binding.textInputPasswordRegister.requestFocus()
                binding.textInputConfirmPasswordRegister.error = getString(R.string.field_didn_t_match)
                binding.textInputConfirmPasswordRegister.requestFocus()
                false
            }
            else -> {
                binding.textInputFullNameRegister.error = null
                binding.textInputEmailRegister.error = null
                binding.textInputPhoneRegister.error = null
                binding.textInputPasswordRegister.error = null
                binding.textInputConfirmPasswordRegister.error = null

                true
            }
        }
    }

    private fun initActionBar() {
        setSupportActionBar(binding.tbRegister)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }
}