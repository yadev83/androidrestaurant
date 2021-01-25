package fr.isen.attia.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.attia.androidrestaurant.databinding.ActivityHomeBinding
import fr.isen.attia.androidrestaurant.databinding.ActivityStartersBinding

class StartersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartersBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}