package com.example.horoscapp.ui.horoscope

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.horoscapp.databinding.FragmentHoroscopeBinding
import com.example.horoscapp.domain.model.HoroscopeInfo
import com.example.horoscapp.domain.model.HoroscopeModel
import com.example.horoscapp.ui.horoscope.adapter.HoroscopeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HoroscopeFragment : Fragment() {

    private val horoscopeViewModel by viewModels<HoroscopeViewModel>()
    private var _binding: FragmentHoroscopeBinding? = null
    private val binding get() = _binding!!

    private lateinit var horoscopeAdapter: HoroscopeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHoroscopeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
    }

    private fun initUI() {
        initRecyclerView()
        initUIState()
    }

    private fun initRecyclerView() {
        horoscopeAdapter = HoroscopeAdapter( onItemSelected = {

            val type = when(it){
                HoroscopeInfo.Aquarius -> HoroscopeModel.Aquarius
                HoroscopeInfo.Aries -> HoroscopeModel.Aries
                HoroscopeInfo.Cancer -> HoroscopeModel.Cancer
                HoroscopeInfo.Capricorn -> HoroscopeModel.Capricorn
                HoroscopeInfo.Gemini -> HoroscopeModel.Gemini
                HoroscopeInfo.Leo -> HoroscopeModel.Leo
                HoroscopeInfo.Libra -> HoroscopeModel.Libra
                HoroscopeInfo.Pisces -> HoroscopeModel.Pisces
                HoroscopeInfo.Sagittarius -> HoroscopeModel.Sagittarius
                HoroscopeInfo.Scorpio -> HoroscopeModel.Scorpio
                HoroscopeInfo.Taurus -> HoroscopeModel.Taurus
                HoroscopeInfo.Virgo -> HoroscopeModel.Virgo
            }

            findNavController().navigate(
                HoroscopeFragmentDirections.actionHoroscopeFragmentToDetailActivity(type)
            )
        })

        binding.rvHoroscope.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = horoscopeAdapter
        }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                horoscopeViewModel.horoscope.collect{
                    horoscopeAdapter.updateList(it)
                }
            }
        }
    }
}