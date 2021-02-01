package fr.isen.attia.androidrestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.attia.androidrestaurant.databinding.ActivityBasketBinding

// J'ai volontairement laissé le AppCompatActivity ici pour que le bouton d'accès au panier
// n'aparaisse pas si on est déjà dans le panier (je trouve ça plus logique)
class BasketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBasketBinding
    private lateinit var basket: Basket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.basket)

        basket = Basket.getBasket(this)

        populateDishList()
    }

    private fun populateDishList(){
        binding.rvBasketItem.layoutManager = LinearLayoutManager(this)
        val adapter = BasketItemAdapter(basket)
        binding.rvBasketItem.adapter = adapter
    }
}