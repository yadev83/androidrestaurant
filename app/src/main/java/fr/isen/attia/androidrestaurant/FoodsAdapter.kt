package fr.isen.attia.androidrestaurant

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FoodsAdapter(private val mFoods : List<FoodModel>) : RecyclerView.Adapter<FoodsAdapter.ViewHolder>() {
    private lateinit var context: Context

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView){
        val nameTextView: TextView = itemView.findViewById<TextView>(R.id.food_name)
        val buyButton: Button = itemView.findViewById<Button>(R.id.buy_button)
        val priceTextView: TextView = itemView.findViewById<TextView>(R.id.food_price)
        val dishImage: ImageView = itemView.findViewById<ImageView>(R.id.food_image)
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

        viewHolder.nameTextView.text = food.name
        viewHolder.priceTextView.text = food.price.toString() + " €"

        val img = viewHolder.dishImage
        Log.d("IMAGE", food.images?.first() as String)
        val imgUrl = food.images?.first()
        if(!imgUrl.isNullOrEmpty()){
            Picasso.with(context).load(imgUrl).into(img)
        }

        val button = viewHolder.buyButton
        button.text = context.getString(R.string.btn_food_details)
        button.isEnabled = true
        button.setOnClickListener{
            Log.d("BUTTON", "Clicked food : "+ food.id)
            val intent = Intent(HomeActivity.context, DetailActivity::class.java).apply{
                putExtra("DISH_ID", food.id)
                putExtra("TITLE", food.name)
            }
            startActivity(HomeActivity.context, intent, null)
        }
    }
}