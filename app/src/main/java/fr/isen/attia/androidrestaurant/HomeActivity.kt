package fr.isen.attia.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import fr.isen.attia.androidrestaurant.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.menuBtnStarters.setOnClickListener{
            Toast.makeText(applicationContext, "Starters button clicked !", Toast.LENGTH_SHORT).show()
        }
        binding.menuBtnMainCourses.setOnClickListener{
            Toast.makeText(applicationContext, "Main Courses button clicked !", Toast.LENGTH_SHORT).show()
        }
        binding.menuBtnDesserts.setOnClickListener{
            Toast.makeText(applicationContext, "Desserts button clicked !", Toast.LENGTH_SHORT).show()
        }
    }
}