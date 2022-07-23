package com.example.businesscard.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.businesscard.App
import com.example.businesscard.databinding.ActivityMainBinding
import com.example.businesscard.util.Image

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }
    private val adapter by lazy { BusinessCardAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.rvCards.adapter = adapter
        getAllBusinessCard()
        insertListener()
    }

    private fun insertListener(){
        binding.btAdd.setOnClickListener {
            val intent = Intent(this, AddBusinessCardActivity::class.java)
            startActivity(intent)
        }
        adapter.listenerShare = { card ->
            Image.share(this, card)
        }
    }

    private fun getAllBusinessCard() {
        mainViewModel.getAll().observe(this) { businessCards ->
            adapter.submitList(businessCards)
        }
    }
}