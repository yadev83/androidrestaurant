package fr.isen.attia.androidrestaurant

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import fr.isen.attia.androidrestaurant.basket.Basket
import fr.isen.attia.androidrestaurant.basket.BasketActivity

/**
 * @brief The base activity parent class of all other activities that need a basket in the top bar
 */
open class BaseActivity: AppCompatActivity(){
    /**
     * @brief When a BaseActivity is created, it creates an option menu in the given menu
     * We add a Basket Icon in the top menu bar and we set text and count (as well as scaling)
     * @param menu the menu in which options shall be created.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuView = menu?.findItem(R.id.menu)?.actionView
        val countText = menuView?.findViewById<TextView>(R.id.basketCount)

        val count = getItemsCount()
        countText?.isVisible = count > 0
        countText?.text = count.toString()

        /** TEXT SCALING BASED ON THE NUMBER OF NUMBERS IN THE COUNT **/
        var tmpCount = count
        var textScale = 1.0F
        while(tmpCount > 10){
            tmpCount /= 10
            textScale -= 0.15F
        }
        if(textScale <= 0)
            textScale = 0.15F
        countText?.textScaleX = textScale

        /** THIS CLICK LISTENER STARTS A BASKET ACTIVITY WHEN CLICKED ON BASKET ICON **/
        menuView?.setOnClickListener{
            ContextCompat.startActivity(this, Intent(this, BasketActivity::class.java), null)
        }

        return true
    }

    /**
     * @brief onResume is called when we come back to this activity
     * It is called for example when you go inside another activity from this one and go back
     * using the "back" button of the android UI. The activity isn't recreated, only resumed
     */
    override fun onResume(){
        super.onResume()
        invalidateOptionsMenu()
    }

    /**
     * @brief Gets the number of items in the basket at any time in this activity.
     * It loads the count from shared preferences, so the storage needs to be updated to see a
     * difference in the items count of this function
     */
    private fun getItemsCount(): Int {
        val sharedPreferences = getSharedPreferences(Basket.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(Basket.ITEMS_COUNT, 0)
    }
}