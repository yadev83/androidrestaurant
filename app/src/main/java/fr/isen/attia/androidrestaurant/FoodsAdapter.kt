package fr.isen.attia.androidrestaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodsAdapter(private val mFoods : List<FoodModel>) : RecyclerView.Adapter<FoodsAdapter.ViewHolder>() {
    private lateinit var context: Context

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView){
        val nameTextView: TextView = itemView.findViewById<TextView>(R.id.food_name)
        val buyButton: Button = itemView.findViewById<Button>(R.id.buy_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val foodView = inflater.inflate(R.layout.item_food, parent, false)

        return ViewHolder(foodView)
    }

    override fun getItemCount(): Int {
        return mFoods.size
    }

    override fun onBindViewHolder(viewHolder: FoodsAdapter.ViewHolder, position: Int) {
        val food: FoodModel = mFoods.get(position)

        val textView = viewHolder.nameTextView
        textView.text = food.name
        val button = viewHolder.buyButton
        button.text = context.getString(R.string.btn_food_details)
        button.isEnabled = true
    }
}