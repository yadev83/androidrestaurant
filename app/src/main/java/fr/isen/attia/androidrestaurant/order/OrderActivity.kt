package fr.isen.attia.androidrestaurant.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import fr.isen.attia.androidrestaurant.R
import fr.isen.attia.androidrestaurant.basket.*
import fr.isen.attia.androidrestaurant.databinding.ActivityOrderBinding

class OrderActivity : AppCompatActivity(), BasketItemInterface {
    private lateinit var binding: ActivityOrderBinding
    private var user = UserAccount()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*user.loginRequest(this)
        if(user.get() == null){
            finish()
            startActivity(Intent(this, RegisterActivity::class.java), null)
        }*/

        reloadData()
        binding.logoutBtn.setOnClickListener{
            user.logout(this)
            finish()
            startActivity(intent)
        }

        val name = user.get()?.email

        title = getString(R.string.order)
    }

    private fun reloadData(){
        val basket = Basket.getBasket(this)
        binding.rvBasketRecap.layoutManager = LinearLayoutManager(this)
        binding.rvBasketRecap.adapter = BasketAdapter(basket, this)
    }

    override fun onDeleteItem(item: BasketItem) {
        val basket = Basket.getBasket(this)
        val itemToDelete = basket.items.firstOrNull{it.food.id == item.food.id }
        basket.items.remove(itemToDelete)
        basket.save(this)

        //After removing and saving the basket, we reload
        reloadData()

        //A lil snackbar to grab some style points
        Snackbar.make(binding.root, getString(R.string.basket_deletion), Snackbar.LENGTH_SHORT).show()
    }

    override fun onShowDetail(item: BasketItem) {
        TODO("Not yet implemented")
    }
}