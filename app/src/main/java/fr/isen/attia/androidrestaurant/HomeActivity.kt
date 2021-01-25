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
            Toast.makeText(this, "Starters button clicked !", Toast.LENGTH_SHORT).show()
        }
        binding.menuBtnMainCourses.setOnClickListener{
            Toast.makeText(this, "Main Courses button clicked !", Toast.LENGTH_SHORT).show()
        }
        binding.menuBtnDesserts.setOnClickListener{
            Toast.makeText(this, "Desserts button clicked !", Toast.LENGTH_SHORT).show()
        }
    }
}