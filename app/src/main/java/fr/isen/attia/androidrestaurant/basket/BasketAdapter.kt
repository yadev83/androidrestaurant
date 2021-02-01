package fr.isen.attia.androidrestaurant.basket

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.attia.androidrestaurant.R
import fr.isen.attia.androidrestaurant.databinding.BasketItemBinding

/**
 * @brief The adapter to put a Basket in its ViewHolder
 * @param basket The basket to adapt
 * @param delegate The delegate object that will allow deletion / details
 */
class BasketAdapter(
        private val basket: Basket,
        private val delegate: BasketItemInterface):
        RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    /**
     * @brief The context is needed mainly to get String consts and to inflate the layout
     */
    lateinit var context: Context

    /**
     * @brief This inner class is the ViewHolder used by the adapter to build up a Basket Item
     * @param binding The binding used to access the elements of the BasketItem view
     */
    inner class BasketViewHolder(binding: BasketItemBinding) : RecyclerView.ViewHolder(binding.root){
        /**
         * @brief All these private variables aren't really necessary but they make code easier
         * They basically get references to the different items of the Activity/View
         */
        private val itemName = binding.basketItemName
        private val itemPrice = binding.basketItemPrice
        private val itemImage = binding.basketItemImage
        private val itemQty = binding.basketItemQty
        private val itemDeleteBtn = binding.basketItemDelete

        /**
         * @brief Binds the given item to the View
         * @param item The BasketItem to bind
         */
        fun bind(item: BasketItem){
            var priceText = item.food.price + " € "
            if(item.count > 1)
                priceText += context.getString(R.string.total_label_start) + (item.food.price?.toFloat()?.times(item.count)).toString() + context.getString(R.string.total_label_end)

            itemName.text = item.food.name
            itemPrice.text = priceText
            itemQty.text = context.getString(R.string.quantity_basket) + item.count.toString()
            itemDeleteBtn.setOnClickListener{
                delegate.onDeleteItem(item)
            }

            //Using picasso for the image (And checking if there is no image, we don't load)
            val imgUrl = item.food.images?.first()
            if(!imgUrl.isNullOrEmpty()){
                Picasso.with(context).load(imgUrl).into(itemImage)
            }
        }
    }

    /**
     * @brief Creates the viewHolder
     * @param parent The view in which we want to create this new activity
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        context = parent.context
        return BasketViewHolder(BasketItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    /**
     * @brief Gets the number of items in the Basket
     * It is not the TOTAL number of items as we know it in the Basket class
     * It is the number of entries in the Basket. Meaning that if we have the same food
     * say for example, 10 times, it will only count for 1 !
     */
    override fun getItemCount(): Int {
        return basket.items.count()
    }

    /**
     * @brief Binds the item in a specific position to the given view Holder
     * It basically just calls the holder.bind method on the given BasketItem
     */
    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(basket[position])
    }
}