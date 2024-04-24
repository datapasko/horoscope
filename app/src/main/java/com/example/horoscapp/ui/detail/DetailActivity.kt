package com.example.horoscapp.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import com.example.horoscapp.R
import com.example.horoscapp.databinding.ActivityDetailBinding
import com.example.horoscapp.domain.model.HoroscopeModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()

    private val args: DetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detailViewModel.getHoroscope(args.type)
        initUI()
    }

    private fun initUI() {
        initListeners()
        initUIState()
    }

    private fun initListeners() {
        binding.ivBack.setOnClickListener{ onBackPressed() }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                detailViewModel.state.collect{
                    when(it){
                        DetailState.Loading -> loadingState()
                        is DetailState.Error -> errorState()
                        is DetailState.Success -> successState(it)
                    }
                }
            }
        }
    }

    private fun successState(state: DetailState.Success) {
        binding.apply {
            tvDetail.text = state.sign
            tvBody.text = state.prediction
            binding.pb.isVisible = false

            val image = when(state.horoscopeModel){
                HoroscopeModel.Aries -> R.drawable.detail_aries
                HoroscopeModel.Taurus -> R.drawable.detail_taurus
                HoroscopeModel.Gemini -> R.drawable.detail_gemini
                HoroscopeModel.Cancer -> R.drawable.detail_cancer
                HoroscopeModel.Leo -> R.drawable.detail_leo
                HoroscopeModel.Virgo -> R.drawable.detail_virgo
                HoroscopeModel.Libra -> R.drawable.detail_libra
                HoroscopeModel.Scorpio -> R.drawable.detail_scorpio
                HoroscopeModel.Sagittarius -> R.drawable.detail_sagittarius
                HoroscopeModel.Capricorn -> R.drawable.detail_capricorn
                HoroscopeModel.Aquarius -> R.drawable.detail_aquarius
                HoroscopeModel.Pisces -> R.drawable.detail_pisces
            }
            ivDetail.setImageResource(image)
        }

    }

    private fun errorState() {
        binding.pb.isVisible = false
    }

    private fun loadingState() {
        binding.pb.isVisible = true
    }
}