package fr.isen.attia.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.attia.androidrestaurant.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private val TAG = "Category"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
    }
}