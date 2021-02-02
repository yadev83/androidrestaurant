package fr.isen.attia.androidrestaurant.food

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.attia.androidrestaurant.R

/**
 * @brief This class adapts a list of FoodModels to an item_food.xml layout to use in recyclerView
 *
 * @param mFoods A list of FoodModel items
 * @param dishClickListener A handler to detect the click on the Food item and show its details page
 */
class FoodsAdapter(private val mFoods : List<FoodModel>,
                   private val dishClickListener: (FoodModel) -> Unit)
    : RecyclerView.Adapter<FoodsAdapter.ViewHolder>() {

    /**
     * @brief As always, the context will be necessary at some point in time
     */
    private lateinit var context: Context

    /**
     * @brief This inner class holds a single item view to be put in the RecyclerView
     * @param listItemView The view of an item in the list to be filled in.
     */
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView){
        /**
         * Private members gathering references to textviews and layout
         */
        private val nameTextView: TextView = itemView.findViewById<TextView>(R.id.food_name)
        private val priceTextView: TextView = itemView.findViewById<TextView>(R.id.food_price)
        private val dishImage: ImageView = itemView.findViewById<ImageView>(R.id.food_image)
        private val layout = itemView.rootView

        /**
         * @brief This method binds a foodModel item to this single item view
         * @param food The FoodModel to bind to the View
         */
        fun bind(food: FoodModel){
            //See the usage of the handler here to handle the click and invoke the food detail
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

    /**
     * @brief This method creates a new ViewHolder
     * @param parent the Parent view (the one in which we want to put the ViewHolder we create)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val foodView = inflater.inflate(R.layout.item_food, parent, false)

        return ViewHolder(foodView)
    }

    /**
     * @brief Returns the size of the current list of foods
     */
    override fun getItemCount(): Int {
        return mFoods.size
    }

    /**
     * @brief This method takes a viewHolder and a position to bind a food Item to it
     * @param viewHolder The view in which we want to put our FoodModel
     * @param position The position in the mFoods array from which we want the Food
     */
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val food: FoodModel = mFoods[position]

        viewHolder.bind(food)
    }
}