package fr.isen.attia.androidrestaurant.parents

import android.content.Intent
import android.view.Menu
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import fr.isen.attia.androidrestaurant.R
import fr.isen.attia.androidrestaurant.order.OrderActivity

/**
 * @brief This is the base activity that holds the "Order" button in the top menu bar
 * It is used by the basket activity to make the transition to the login/register page
 */
open class OrderBaseActivity: AppCompatActivity() {

    /**
     * @brief This method is called on activity creation
     * It creates the options menu and sets the click listener to redirect you to the OrderActivity
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.order_menu, menu)
        val menuView = menu?.findItem(R.id.orderMenuBtn)?.actionView

        menuView?.findViewById<Button>(R.id.orderBtn)?.setOnClickListener{
            ContextCompat.startActivity(this, Intent(this, OrderActivity::class.java), null)
        }
        return true
    }
}