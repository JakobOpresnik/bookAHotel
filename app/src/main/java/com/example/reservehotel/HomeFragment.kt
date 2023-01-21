package com.example.reservehotel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.reservehotel.databinding.FragmentHomeBinding
import com.example.reservehotel.hotelUtils.Hotel
import com.example.reservehotel.hotelUtils.Reservation
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.Date
import kotlin.system.exitProcess

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    //private var app = MyApplication()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val view = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
//        app.onCreate()

        val bookHotelButton = binding.bookHotelButton
        bookHotelButton.setOnClickListener {
            findNavController().navigate(R.id.inputFragment)    // navigate to input fragment
        }

        val reservationsButton = binding.reservationsButton
        reservationsButton.setOnClickListener {
            findNavController().navigate(R.id.displayFragment)  // navigate to display fragment
        }

        val mapButton = binding.mapButton
        mapButton.setOnClickListener {
            findNavController().navigate(R.id.mapFragment)
        }

        val settingsButton = binding.settingsButton
        settingsButton.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }

        val aboutButton = binding.aboutButton
        aboutButton.setOnClickListener {
            findNavController().navigate(R.id.aboutFragment)
        }

        val exitButton = binding.exitButton
        exitButton.setOnClickListener {
            activity?.finish()
            exitProcess(0)
        }

        return binding.root
    }


}