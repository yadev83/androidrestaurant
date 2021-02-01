package fr.isen.attia.androidrestaurant

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.attia.androidrestaurant.databinding.BasketItemBinding

class BasketItemAdapter(private val basket: Basket, private val delegate: BasketItemInterface): RecyclerView.Adapter<BasketItemAdapter.BasketViewHolder>() {
    lateinit var context: Context

    inner class BasketViewHolder(binding: BasketItemBinding) : RecyclerView.ViewHolder(binding.root){
        val itemName = binding.basketItemName
        val itemPrice = binding.basketItemPrice
        val itemImage = binding.basketItemImage
        val itemQty = binding.basketItemQty
        val itemDeleteBtn = binding.basketItemDelete

        fun bind(item: BasketItem){
            itemName.text = item.food.name
            itemPrice.text = item.food.price + " €"
            itemQty.text = context.getString(R.string.quantity_basket) + item.count.toString()
            itemDeleteBtn.setOnClickListener{
                delegate.onDeleteItem(item)
            }
            val imgUrl = item.food.images?.first()
            if(!imgUrl.isNullOrEmpty()){
                Picasso.with(context).load(imgUrl).into(itemImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        context = parent.context
        return BasketViewHolder(BasketItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return basket.items.count()
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(basket[position])
    }
}