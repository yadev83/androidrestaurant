package fr.isen.attia.androidrestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import fr.isen.attia.androidrestaurant.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val TAG = "MainMenu"

    companion object{
        const val CATEGORY_NAME = "CATEGORY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "HomeActivity has been created")
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.menuBtnStarters.setOnClickListener{
            //Toast used for debugging only
            //Toast.makeText(this, "Starters button clicked !", Toast.LENGTH_SHORT).show()
            goToCategory(CategoryActivity.ItemType.STARTER)
        }
        binding.menuBtnMainCourses.setOnClickListener{
            //Toast used for debugging only
            //Toast.makeText(this, "Main Courses button clicked !", Toast.LENGTH_SHORT).show()
            goToCategory(CategoryActivity.ItemType.MAIN)
        }
        binding.menuBtnDesserts.setOnClickListener{
            //Toast used for debugging only
            //Toast.makeText(this, "Desserts button clicked !", Toast.LENGTH_SHORT).show()
            goToCategory(CategoryActivity.ItemType.DESSERT)
        }
    }

    override fun onDestroy() {
        Log.i(TAG, "HomeActivity has been destroyed")
        super.onDestroy()
    }

    private fun goToCategory(type: CategoryActivity.ItemType){
        Log.i(TAG, "Opening new activity with $type identifier")
        val intent = Intent(this, CategoryActivity::class.java).apply{
            putExtra(CATEGORY_NAME, type)
        }
        startActivity(intent)
    }
}