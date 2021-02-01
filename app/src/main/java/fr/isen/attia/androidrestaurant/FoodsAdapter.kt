package fr.isen.attia.androidrestaurant

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FoodsAdapter(private val mFoods : List<FoodModel>,
                   private val dishClickListener: (FoodModel) -> Unit)
    : RecyclerView.Adapter<FoodsAdapter.ViewHolder>() {
    private lateinit var context: Context

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView){
        private val nameTextView: TextView = itemView.findViewById<TextView>(R.id.food_name)
        private val priceTextView: TextView = itemView.findViewById<TextView>(R.id.food_price)
        private val dishImage: ImageView = itemView.findViewById<ImageView>(R.id.food_image)
        private val layout = itemView.rootView

        fun bind(food: FoodModel){
            layout.setOnClickListener{
                dishClickListener.invoke(food)
            }

            nameTextView.text = food.name
            priceTextView.text = food.price.toString() + " €"

            val img = dishImage
            Log.d("IMAGE", food.images?.first() as String)
            val imgUrl = food.images?.first()
            if(!imgUrl.isNullOrEmpty()){
                Picasso.with(context).load(imgUrl).into(img)
            }
        }
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

        viewHolder.bind(food)
    }
}