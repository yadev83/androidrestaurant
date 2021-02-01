package fr.isen.attia.androidrestaurant.basket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import fr.isen.attia.androidrestaurant.R
import fr.isen.attia.androidrestaurant.databinding.ActivityBasketBinding

/**
 * @brief This class is the Activity displayed when looking at our Basket in the app
 * It inherits from AppCompatActivity() because it is an Activity from the app BUT,
 * because it is the Activity for the Basket, it is not a child of "BaseActivity" as we do not
 * want the Basket to be displayed on top of it.
 * It also inherits from BasketItemInterface (cf Basket.kt) because it has the need for
 * BasketItem management.
 */
class BasketActivity : AppCompatActivity(), BasketItemInterface {
    /**
     * @brief The binding used to access the different elements of the Activity
     */
    private lateinit var binding: ActivityBasketBinding

    /**
     * @brief The basket to be displayed
     */
    private lateinit var basket: Basket

    /**
     * @brief onCreate is called when the Activity is created, inherited from AppCompatActivity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //We initialize the binding and set the contentView and title
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.basket)

        //Reloads data once here. (It is more like an initial loading in this case)
        reloadData()
    }

    /**
     * @brief Reloads the data from the Basket
     * It gets the most recent basket and initializes the adapter for it. When editing the Basket
     * this should be called just after to update the View.
     */
    private fun reloadData(){
        basket = Basket.getBasket(this)
        binding.rvBasketItem.layoutManager = LinearLayoutManager(this)
        binding.rvBasketItem.adapter = BasketAdapter(basket, this)
    }

    /**
     * @brief Deletes the given item
     * @param item This is the item to remove from the Basket
     */
    override fun onDeleteItem(item: BasketItem){
        val basket = Basket.getBasket(this)
        val itemToDelete = basket.items.firstOrNull{it.food.id == item.food.id }
        basket.items.remove(itemToDelete)
        basket.save(this)

        //After removing and saving the basket, we reload
        reloadData()

        //A lil snackbar to grab some style points
        Snackbar.make(binding.root, getString(R.string.basket_deletion), Snackbar.LENGTH_SHORT).show()
    }

    /**
     * @brief The method is called when we want to get the details of the BasketItem
     * @param item The item we want to get details from
     */
    override fun onShowDetail(item: BasketItem) {
        TODO("Not yet implemented")
    }
}