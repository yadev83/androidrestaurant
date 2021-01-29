package fr.isen.attia.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import fr.isen.attia.androidrestaurant.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var food: SerializedFood

    private var id: Int? = 0
    private lateinit var carousel: CarouselView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        food = intent.getSerializableExtra("DISH") as SerializedFood
        Log.d("SERIALIZED_FOOD", food.toString())

        populateCarousel()

        binding.dishPriceText.text = food.price + "€"

        var ingredientsList = ""
        food.ingredients?.forEach { ingredient ->
            ingredientsList += "- $ingredient\n"
        }
        binding.ingredientsTextview.text = ingredientsList
    }

    private fun populateCarousel(){
        carousel = binding.root.findViewById(R.id.carouselView)
        carousel.pageCount = food.images?.size ?: 0

        val imageListener: ImageListener = ImageListener{ i: Int, imageView: ImageView ->
            val imgUrl = food.images?.get(i)
            if(!imgUrl.isNullOrEmpty()){
                Picasso.with(this).load(imgUrl).into(imageView)
            }
        }

        carousel.setImageListener(imageListener)
    }
}