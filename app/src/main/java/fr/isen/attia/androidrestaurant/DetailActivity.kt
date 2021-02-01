package fr.isen.attia.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ImageListener
import fr.isen.attia.androidrestaurant.databinding.ActivityDetailBinding
import kotlin.math.max

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var food: SerializedFood

    private var id: Int? = 0

    private var orderQuantity = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        food = intent.getSerializableExtra("DISH") as SerializedFood
        Log.d("SERIALIZED_FOOD", food.toString())

        //populateCarousel()
        populateCarouselViewPager()
        populateTextViews()
        populateShopLayout()
    }

    private fun populateCarousel(){
        val carouselPager = binding.carouselViewPager
        binding.root.removeView(carouselPager)

        val carousel = binding.carouselView
        carousel.pageCount = food.images?.size ?: 0

        var noImages: Boolean = true
        food.images?.forEach { imageURL ->
            if(!imageURL.isNullOrEmpty()){
                noImages = false
            }
        }

        var constraintSet = ConstraintSet()
        constraintSet.clone(binding.root)
        constraintSet.connect(binding.dishName.id, ConstraintSet.TOP, carousel.id, ConstraintSet.BOTTOM)
        constraintSet.applyTo(binding.root);

        if(noImages) {
            binding.root.removeView(carousel)
        }else{
            val imageListener: ImageListener = ImageListener{ i: Int, imageView: ImageView ->
                val imgUrl = food.images?.get(i)
                if(!imgUrl.isNullOrEmpty()){
                    Picasso.with(this).load(imgUrl).into(imageView)
                }
            }
            carousel.setImageListener(imageListener)
        }
    }

    private fun populateCarouselViewPager(){
        val carousel = binding.carouselView
        binding.root.removeView(carousel)

        var noImages: Boolean = true
        food.images?.forEach { imageURL ->
            if(!imageURL.isNullOrEmpty()){
                noImages = false
            }
        }

        if(noImages){
            binding.root.removeView(carousel)
        }else{
            val carouselPager = binding.carouselViewPager
            carouselPager.adapter = CarouselAdapter(this, food.images as List<String>)

            var constraintSet = ConstraintSet()
            constraintSet.clone(binding.root)
            constraintSet.connect(binding.dishName.id, ConstraintSet.TOP, carouselPager.id, ConstraintSet.BOTTOM)
            constraintSet.applyTo(binding.root);
        }
    }

    private fun populateTextViews(){
        binding.dishName.text = food.name

        food.ingredients?.forEach { ingredient ->
            var textView: TextView = TextView(this)
            textView.text = "- $ingredient"
            textView.textSize = 24.0F
            binding.ingredientsLayout.addView(textView)
        }
    }

    private fun populateShopLayout(){
        binding.minusBtn.setOnClickListener{
            orderQuantity = max(1, orderQuantity-1)
            refreshShop()
        }
        binding.plusBtn.setOnClickListener{
            orderQuantity += 1
            refreshShop()
        }

        binding.addToBasketButton.setOnClickListener{
            addToBasket(food, orderQuantity)
        }

        refreshShop()
    }

    private fun refreshShop(){
        binding.qtyTextV.text = orderQuantity.toString()
        var subtotalPrice: Float = (food.price?.toFloat() ?: 0.0F)
        subtotalPrice = subtotalPrice?.times(orderQuantity)
        binding.totalPriceV.text = subtotalPrice.toString() + "€"
    }

    private fun addToBasket(food: SerializedFood, count: Int){
        val basket = Basket.getBasket(this)
        basket.addItem(BasketItem(food, count))
        basket.save(this)
        val json = GsonBuilder().create().toJson(basket)
        Snackbar.make(binding.root, getString(R.string.basket_validation), Snackbar.LENGTH_SHORT).show()
    }
}