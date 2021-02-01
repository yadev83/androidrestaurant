package fr.isen.attia.androidrestaurant.basket

import android.content.Context
import com.google.gson.GsonBuilder
import fr.isen.attia.androidrestaurant.food.SerializedFood
import java.io.File
import java.io.Serializable

/**
 * @brief Describes a Basket to be used in our restaurant app
 * It is composed of a list of Basket Items, each of which is in fact a Dish and a Quantity.
 *
 * @param items The list of BasketItems to use in this Basket's constructor
 */
class Basket(val items: MutableList<BasketItem>): Serializable {
    /**
     * @brief Counts the TOTAL number of items in a Basket.
     * It is an aggregation of all the quantities for each dish of the Basket
     */
    private var itemsCount: Int = 0
        get() {
            return if(items.count() > 0){
                items.map{ it.count }.reduce{acc, i -> acc + i}
            }else{
                0
            }
        }

    /**
     * @brief Adds an item to the basket
     * This method will ensure the item is not in the Basket before creating it.
     * If it is already existing, the function will just increment the counter instead.
     * @param item The BasketItem to add to the basket.
     */
    fun addItem(item: BasketItem){
        //Get Existing Item here
        val existingItem = items.firstOrNull(){
            it.food.id == item.food.id
        }

        existingItem?.let{
            //Do this if the Item already exists (increment only)
            existingItem.count += item.count
        } ?: run {
            //Do that if the item doesn"t exist and shall be created from scratch
            items.add(item)
        }
    }

    /**
     * @brief Saves the current Basket into a JSON file
     * Also uses sharedPreferences to store the number of Items to update the counter in action bar
     * @param context The context of the app to get access to sharedPreferences from this class
     */
    fun save(context: Context){
        val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ITEMS_COUNT, itemsCount)
        editor.apply()

        val jsonFile = File(context.cacheDir.absolutePath + BASKET_FNAME)
        jsonFile.writeText(GsonBuilder().create().toJson(this))
    }

    /**
     * @brief Returns the item at the given position (for Adapters mainly)
     * This method allows the usage of the "array" notation to get items.
     */
    operator fun get(position: Int): BasketItem {
        return items[position]
    }

    /**
     * @brief A companion object to hold static components of our Basket
     * Contains const strings for file management and a static method to get the Basket from file
     */
    companion object{
        /**
         * Const strings to write to/read from the shared preferences and the json save file
         */
        const val BASKET_FNAME = "basket.json"
        const val ITEMS_COUNT = "Items count"
        const val USER_PREFERENCES_NAME = "Users Preferences Name"

        /**
         * @brief Gets the most recently saved basket from json file (cf. BASKET_FNAME)
         * @param context The context is needed to get the cache of the app
         * @return The freshly loaded Basket class
         */
        fun getBasket(context: Context): Basket {
            val jsonFile = File(context.cacheDir.absolutePath + BASKET_FNAME)

            /**
             * Here, we check if the file exists or not. Indeed if the file isn't here, or is not
             * accessible, we can safely create our own empty Basket.
             */
            return if(jsonFile.exists()){
                val json = jsonFile.readText()
                GsonBuilder().create().fromJson(json, Basket::class.java)
            }else{
                Basket(mutableListOf())
            }
        }
    }
}

/**
 * @brief This interface describes the behaviour of a BasketItem
 */
interface BasketItemInterface{
    /**
     * @brief This method shall be called whenever we delete an item from a Basket
     */
    fun onDeleteItem(item: BasketItem)

    /**
     * @brief This method shall be called everytime we click on a basket item to get details
     */
    fun onShowDetail(item: BasketItem)
}

/**
 * @brief This class describes a BasketItem
 * A Basket Item is composed of a clearly defined dish and a counter (quantity)
 * @param food A SerializedFood object to be used as a reference for our dish
 * @param count The number of this specific food we want to put in the Basket
 */
class BasketItem(val food: SerializedFood, var count: Int): Serializable{}