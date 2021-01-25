package fr.isen.attia.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.attia.androidrestaurant.databinding.ActivityMainCoursesBinding
import fr.isen.attia.androidrestaurant.databinding.ActivityStartersBinding

class MainCoursesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainCoursesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainCoursesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}