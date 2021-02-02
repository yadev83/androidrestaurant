package fr.isen.attia.androidrestaurant.food

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ImageListener
import fr.isen.attia.androidrestaurant.BaseActivity
import fr.isen.attia.androidrestaurant.R
import fr.isen.attia.androidrestaurant.basket.Basket
import fr.isen.attia.androidrestaurant.basket.BasketItem
import fr.isen.attia.androidrestaurant.carousel.CarouselAdapter
import fr.isen.attia.androidrestaurant.databinding.ActivityDetailBinding
import kotlin.math.max

/**
 * @brief FoodDetailActivity defines an activity to show detail about a food item
 * It extends BaseActivity so we have the basket available in the top bar
 */
class FoodDetailActivity : BaseActivity() {
    /**
     * @brief A binding to be used  for linking to the activity_detail.xml layout
     */
    private lateinit var binding: ActivityDetailBinding

    /**
     * @brief A serializedFood item to be used for display purposes
     */
    private lateinit var food: SerializedFood

    /**
     * @brief The quantity of items to order to be shown on the activity
     * Defaulted to 1 (because we can't order less than 1 item)
     * And will change based on the "+" and "-" buttons pressed by the user.
     */
    private var orderQuantity = 1

    /**
     * @brief This method is called on class creation (i.e when an activity_detail is loaded)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //We init the binding
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Gets the serialized food from the Extras of the intent
        //Note that we cast is as SerializedFood, that is why we needed a Serialized data class.
        food = intent.getSerializableExtra("DISH") as SerializedFood

        /**
         * These methods populate the view with different things
         * - populateCarousel() and populateCarouselViewPager() does populate the image carousel
         * - populateTextViews() fills in all the available text views with the food details
         * - populateShopLayout() sets click listeners on buttons and fills anything "shop" related
         * - refreshShop() refreshes the view with values from the stored details about shop
         */
        populateCarousel() //populateCarouselViewPager()
        populateTextViews()
        populateShopLayout()
        refreshShop()
    }

    /**
     * @brief This method uses an external lib to show a picture carousel.
     * I find it more pleasant visually than the view pager. Thus I favor it over my custom carousel
     */
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

    /**
     * @brief This method creates a carousel using only internal tools (viewPager2)
     * It is working great but the external lib make it look better so I don't use this one atm
     */
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
            carouselPager.adapter = CarouselAdapter(this, food.images as List<String?>)

            var constraintSet = ConstraintSet()
            constraintSet.clone(binding.root)
            constraintSet.connect(binding.dishName.id, ConstraintSet.TOP, carouselPager.id, ConstraintSet.BOTTOM)
            constraintSet.applyTo(binding.root);
        }
    }

    /**
     * @brief This method adds food name and ingredients to the view
     */
    private fun populateTextViews(){
        binding.dishName.text = food.name

        food.ingredients?.forEach { ingredient ->
            var textView = TextView(this)
            textView.text = "- $ingredient"
            textView.textSize = 24.0F
            binding.ingredientsLayout.addView(textView)
        }
    }

    /**
     * @brief This method gives an action to all the buttons of the shop
     * Buttons are the "+" and "-" buttons to add or remove an item. Or even the "Order" button.
     */
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


    }

    /**
     * @brief Refreshes the shop view with quantities and subtotal from stored preferences and json.
     */
    private fun refreshShop(){
        binding.qtyTextV.text = orderQuantity.toString()
        var subtotalPrice: Float = (food.price?.toFloat() ?: 0.0F)
        subtotalPrice = subtotalPrice.times(orderQuantity)
        binding.totalPriceV.text = subtotalPrice.toString() + "€"
    }

    /**
     * @brief Refreshes the topbar menu.
     * This method simply saves the given basket to a file and invalidate the options menu
     * By invalidating the menu, we force a recreation of it, thus we refresh the visuals
     */
    private fun refreshMenu(basket: Basket){
        basket.save(this)
        invalidateOptionsMenu()
    }

    /**
     * @brief Adds the given item to the basket (with the given count as a quantity)
     * It adds to the basket and it then saves the basket and refreshes the menu and shop
     */
    private fun addToBasket(food: SerializedFood, count: Int){
        val basket = Basket.getBasket(this)
        basket.addItem(BasketItem(food, count))
        refreshMenu(basket)

        //A simple snackbar to inform that an item has been added to basket (style points)
        Snackbar.make(binding.root, getString(R.string.basket_validation), Snackbar.LENGTH_SHORT).show()
    }
}