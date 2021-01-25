package fr.isen.attia.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.attia.androidrestaurant.databinding.ActivityDessertsBinding

class DessertsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDessertsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDessertsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}