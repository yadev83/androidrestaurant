package fr.isen.attia.androidrestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import fr.isen.attia.androidrestaurant.databinding.ActivityBasketBinding

// J'ai volontairement laissé le AppCompatActivity ici pour que le bouton d'accès au panier
// n'aparaisse pas si on est déjà dans le panier (je trouve ça plus logique)
class BasketActivity : AppCompatActivity(), BasketItemInterface {
    private lateinit var binding: ActivityBasketBinding
    private lateinit var basket: Basket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.basket)

        reloadData()
    }

    private fun reloadData(){
        basket = Basket.getBasket(this)
        binding.rvBasketItem.layoutManager = LinearLayoutManager(this)
        binding.rvBasketItem.adapter = BasketItemAdapter(basket, this)
    }

    override fun onDeleteItem(item: BasketItem){
        val basket = Basket.getBasket(this)
        val itemToDelete = basket.items.firstOrNull{it.food.id == item.food.id }
        basket.items.remove(itemToDelete)
        basket.save(this)
        reloadData()

        Snackbar.make(binding.root, getString(R.string.basket_deletion), Snackbar.LENGTH_SHORT).show()
    }

    override fun onShowDetail(item: BasketItem) {
        TODO("Not yet implemented")
    }
}