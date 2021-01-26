package fr.isen.attia.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import fr.isen.attia.androidrestaurant.databinding.ActivityCategoryBinding
import fr.isen.attia.androidrestaurant.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private var id: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = intent.getStringExtra("TITLE")
        id = intent.getIntExtra("DISH_ID", 0)

        val food: FoodModel? = FoodModel.getFoodById(id!!)

        if(food != null){
            binding.dishPriceText.text = food.price.toString() + "€"

            var ingredientsList: String = ""
            food.ingredients?.forEach { ingredient ->
                ingredientsList += "- $ingredient\n"
            }
            binding.ingredientsTextview.text = ingredientsList
        }else{
            Log.d("DETAIL", "Could not find given dish id in food array")
        }
    }
}