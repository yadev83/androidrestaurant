package fr.isen.attia.androidrestaurant

import android.content.Context
import android.util.Log
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

open class BaseActivity: AppCompatActivity(){
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuView = menu?.findItem(R.id.menu)?.actionView
        val countText = menuView?.findViewById<TextView>(R.id.basketCount)

        val count = getItemsCount()
        countText?.isVisible = count > 0
        countText?.text = count.toString()

        /** TEXT SCALING BASED ON THE NUMBER OF NUMBERS **/
        var tmpCount = count
        var textScale = 1.0F
        while(tmpCount > 10){
            tmpCount /= 10
            textScale -= 0.15F
        }
        if(textScale <= 0)
            textScale = 0.15F
        countText?.textScaleX = textScale

        menuView?.setOnClickListener{
            Log.d("BASKET", "Start basket activity !")
        }

        return true
    }

    private fun getItemsCount(): Int {
        val sharedPreferences = getSharedPreferences(DetailActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(DetailActivity.ITEMS_COUNT, 0)
    }
}