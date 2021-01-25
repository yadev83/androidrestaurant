package fr.isen.attia.androidrestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import fr.isen.attia.androidrestaurant.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val TAG = "MainMenu"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "HomeActivity has been created")
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.menuBtnStarters.setOnClickListener{
            //Toast used for debugging only
            //Toast.makeText(this, "Starters button clicked !", Toast.LENGTH_SHORT).show()
            showStartersMenu()
        }
        binding.menuBtnMainCourses.setOnClickListener{
            //Toast used for debugging only
            //Toast.makeText(this, "Main Courses button clicked !", Toast.LENGTH_SHORT).show()
            showMainCoursesMenu()
        }
        binding.menuBtnDesserts.setOnClickListener{
            //Toast used for debugging only
            //Toast.makeText(this, "Desserts button clicked !", Toast.LENGTH_SHORT).show()
            showDessertsMenu()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "HomeActivity has been destroyed")
    }

    private fun showStartersMenu(){
        val intent = Intent(this, StartersActivity::class.java)
        startActivity(intent)
    }
    
    private fun showDessertsMenu(){
        val intent = Intent(this, DessertsActivity::class.java)
        startActivity(intent)
    }

    private fun showMainCoursesMenu(){
        val intent = Intent(this, MainCoursesActivity::class.java)
        startActivity(intent)
    }
}