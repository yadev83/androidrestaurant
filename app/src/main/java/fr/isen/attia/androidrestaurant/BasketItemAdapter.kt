package fr.isen.attia.androidrestaurant

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BasketItemAdapter(private val mItems : List<BasketItem>): RecyclerView.Adapter<BasketItemAdapter.ViewHolder>() {
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView){
        private val nameTextView: TextView = itemView.findViewById<TextView>(R.id.food_name)
        private val priceTextView: TextView = itemView.findViewById<TextView>(R.id.food_price)
        private val dishImage: ImageView = itemView.findViewById<ImageView>(R.id.food_image)
        private val layout = itemView.rootView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}