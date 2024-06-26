package com.sbarifp.pemasaranproduk.presentation.sell

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.sbarifp.pemasaranproduk.R
import com.sbarifp.pemasaranproduk.data.model.Resource
import com.sbarifp.pemasaranproduk.data.model.product.CreateAdsRequest
import com.sbarifp.pemasaranproduk.databinding.ActivitySellBinding
import com.sbarifp.pemasaranproduk.presentation.location.LocationActivity
import com.sbarifp.pemasaranproduk.presentation.uploadphoto.UploadPhotoActivity
import com.sbarifp.pemasaranproduk.utils.convertToAddress
import com.sbarifp.pemasaranproduk.utils.showDialogError
import com.sbarifp.pemasaranproduk.utils.showDialogLoading
import com.sbarifp.pemasaranproduk.utils.showDialogNotification
import com.sbarifp.pemasaranproduk.utils.showDialogSuccess
import com.sbarifp.pemasaranproduk.utils.startActivity

class SellActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySellBinding
    private var location: LatLng? = null
    private lateinit var dialogLoading: AlertDialog
    private lateinit var sellViewModel: SellViewModel
    private var isEdit = false
    private lateinit var token: String
    private var idProduct = 0

    private val startMapResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == Activity.RESULT_OK){
            val data = it.data
            val mLocation = data?.getParcelableExtra<LatLng>(LocationActivity.EXTRA_LOCATION)
            if (mLocation != null){
                location = mLocation
                val address = location?.convertToAddress(this)
                binding.etAddressSell.setText(address.toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialogLoading = showDialogLoading(this)
        sellViewModel = ViewModelProvider(this).get(SellViewModel::class.java)

        onAction()
    }

    private fun onAction() {
        binding.btnMapSell.setOnClickListener {
            val intent = Intent(this, LocationActivity::class.java)
            startMapResult.launch(intent)
        }

        binding.btnSubmitSell.setOnClickListener {
            val title = binding.etTitleProductSell.text.toString().trim()
            val brand = binding.etBrandSell.text.toString().trim()
            val model = binding.etModelProductSell.text.toString().trim()
            val yearsProduction = binding.etYearProductSell.text.toString().trim()
            val price = binding.etPriceProductSell.text.toString().trim()
            val address = binding.etAddressSell.text.toString().trim()
            val desc = binding.etDescProductSell.text.toString().trim()
            val location = location
            var isNew = false

            val selectId = binding.rgConditionSell.checkedRadioButtonId
            if (selectId == R.id.rb_new_sell){
                isNew = true
            }

            if (checkValid(title, brand, model, yearsProduction, price, location, address, desc)){
                if (isEdit){
                    updateAdsToSever(
                        title,
                        brand,
                        model,
                        yearsProduction,
                        price,
                        location,
                        isNew,
                        address,
                        desc
                    )
                }else{
                    createAdsToSever(
                        title,
                        brand,
                        model,
                        yearsProduction,
                        price,
                        location,
                        isNew,
                        address,
                        desc
                    )
                }
            }
        }

        binding.tbSell.setNavigationOnClickListener { onBackPressed() }
    }

    private fun updateAdsToSever(
        title: String,
        brand: String,
        model: String,
        yearsProduction: String,
        price: String,
        location: LatLng?,
        isNew: Boolean,
        address: String,
        desc: String
    ) {
        val createAdsRequest = CreateAdsRequest(
            title = title,
            brand = brand,
            model = model,
            year = yearsProduction,
            condition = isNew,
            price = price.toInt(),
            address = address,
            locLatitude = location?.latitude,
            locLongitude = location?.longitude,
            categoryId = 3,
            description = desc,
            sold = false
        )

        val body = Gson().toJson(createAdsRequest)

        sellViewModel.updateAds(token, body, idProduct).observe(this){ state ->
            when(state){
                Resource.Empty -> {
                    hideLoading()
                    showDialogNotification(this, "EMPTY")
                }
                is Resource.Error -> {
                    hideLoading()
                    val errorMessage = state.errorMessage
                    showDialogError(this, errorMessage)
                }
                Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    hideLoading()
                    val data = state.data
                    val dialogSuccess = showDialogSuccess(this, data.message.toString())
                    dialogSuccess.show()

                    Handler(Looper.getMainLooper()).postDelayed({
                        dialogSuccess.dismiss()
                        finish()
                    }, 2000)
                }
            }
        }
    }

    private fun createAdsToSever(
        title: String,
        brand: String,
        model: String,
        yearsProduction: String,
        price: String,
        location: LatLng?,
        isNew: Boolean,
        address: String,
        desc: String
    ) {
        val createAdsRequest = CreateAdsRequest(
            title = title,
            brand = brand,
            model = model,
            year = yearsProduction,
            condition = isNew,
            price = price.toInt(),
            address = address,
            locLatitude = location?.latitude,
            locLongitude = location?.longitude,
            categoryId = 3,
            description = desc,
            sold = false
        )

        val body = Gson().toJson(createAdsRequest)

        sellViewModel.createAds(token, body).observe(this){ state ->
            when(state){
                Resource.Empty -> {
                    hideLoading()
                    showDialogNotification(this, "EMPTY")
                }
                is Resource.Error -> {
                    hideLoading()
                    val errorMessage = state.errorMessage
                    showDialogError(this, errorMessage)
                }
                Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    hideLoading()
                    val data = state.data
                    val dialogSuccess = showDialogSuccess(this, data.message.toString())
                    dialogSuccess.setCancelable(true)
                    dialogSuccess.show()

                    Handler(Looper.getMainLooper()).postDelayed({
                        dialogSuccess.dismiss()
                        startActivity<UploadPhotoActivity>(
                            UploadPhotoActivity.EXTRA_ADS to data.dataCreateAds,
                            UploadPhotoActivity.EXTRA_IS_EDIT to false
                        )
                    }, 1200)
                }
            }
        }
    }

    private fun showLoading() {
        dialogLoading.show()
    }

    private fun hideLoading() {
        dialogLoading.dismiss()
    }

    private fun checkValid(
        title: String,
        brand: String,
        model: String,
        yearsProduction: String,
        price: String,
        location: LatLng?,
        address: String,
        desc: String
    ): Boolean {
        return when {
            title.isEmpty() -> {
                binding.textInputTitleProductSell.error = getString(R.string.field_title)
                binding.textInputTitleProductSell.requestFocus()
                false
            }

            desc.isEmpty() -> {
                binding.textInputDescProductSell.error = getString(R.string.field_desc)
                binding.textInputDescProductSell.requestFocus()
                false
            }

            brand.isEmpty() -> {
                binding.textInputTitleProductSell.error = null

                binding.textInputBrandSell.error = getString(R.string.field_brand)
                binding.textInputBrandSell.requestFocus()
                false
            }

            model.isEmpty() -> {
                binding.textInputBrandSell.error = null

                binding.textInputModelProductSell.error = getString(R.string.field_model)
                binding.textInputModelProductSell.requestFocus()
                false
            }

            yearsProduction.isEmpty() -> {
                binding.textInputModelProductSell.error = null

                binding.textInputYearProductSell.error = getString(R.string.field_year_production)
                binding.textInputYearProductSell.requestFocus()
                false
            }

            price.isEmpty() -> {
                binding.textInputYearProductSell.error = null

                binding.textInputPriceProductSell.error = getString(R.string.field_price)
                binding.textInputPriceProductSell.requestFocus()
                false
            }

            location == null -> {
                binding.textInputPriceProductSell.error = null

                showDialogError(this, getString(R.string.field_location))
                false
            }

            address.isEmpty() -> {
                binding.textInputAddressSell.error = getString(R.string.field_address)
                binding.textInputAddressSell.requestFocus()
                false
            }

            yearsProduction.isEmpty() -> {
                binding.textInputModelProductSell.error = null

                binding.textInputYearProductSell.error = getString(R.string.field_year_production)
                binding.textInputYearProductSell.requestFocus()
                false
            }
            else -> {
                binding.textInputTitleProductSell.error = null
                binding.textInputBrandSell.error = null
                binding.textInputModelProductSell.error = null
                binding.textInputYearProductSell.error = null
                binding.textInputPriceProductSell.error = null
                binding.textInputAddressSell.error = null
                true
            }
        }
    }
}