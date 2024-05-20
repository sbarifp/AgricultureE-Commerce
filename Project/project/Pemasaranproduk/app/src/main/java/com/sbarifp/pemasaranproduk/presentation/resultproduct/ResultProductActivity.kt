package com.sbarifp.pemasaranproduk.presentation.resultproduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sbarifp.pemasaranproduk.R
import com.sbarifp.pemasaranproduk.databinding.ActivityResultProductBinding

class ResultProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultProductBinding
    private lateinit var resultProductAdapter: ResultProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initResultProductAdapter()
    }

    private fun initResultProductAdapter() {
        resultProductAdapter = ResultProductAdapter()
        binding.rvProductResult.adapter = resultProductAdapter
    }

    companion object{
        const val EXTRA_TITLE = "extra_file"
        const val EXTRA_LOCATION = "extra_location"
    }
}