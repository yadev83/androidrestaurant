package fr.isen.attia.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import fr.isen.attia.androidrestaurant.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private var id: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val food: SerializedFood = intent.getSerializableExtra("DISH") as SerializedFood
        Log.d("SERIALIZED_FOOD", food.toString())

        binding.dishPriceText.text = food.price + "€"

        var ingredientsList = ""
        food.ingredients?.forEach { ingredient ->
            ingredientsList += "- $ingredient\n"
        }
        binding.ingredientsTextview.text = ingredientsList
    }
}