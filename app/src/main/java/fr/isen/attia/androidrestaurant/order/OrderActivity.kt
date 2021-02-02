package fr.isen.attia.androidrestaurant.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.attia.androidrestaurant.R
import fr.isen.attia.androidrestaurant.basket.BasketActivity

class OrderActivity : AppCompatActivity() {
    private var user = UserAccount()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        user.loginRequest(this)
        if(user.get() == null){
            startActivity(Intent(this, RegisterActivity::class.java), null)
        }

        title = getString(R.string.order)
    }
}