package fr.isen.attia.androidrestaurant.carousel

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @brief Adapts a list of images to a ViewPager carousel
 * @param activity The parent activity
 * @param items The list of images url to be used
 */
class CarouselAdapter(activity: AppCompatActivity, private val items: List<String?>) : FragmentStateAdapter(activity) {
    /**
     * @brief Returns the number of urls (images)
     */
    override fun getItemCount(): Int {
        return items.count()
    }

    /**
     * @brief Creates a fragment for a given position in the list
     * Supposing that we have a list of urls, each one is going to be a fragment containing an image
     */
    override fun createFragment(position: Int): Fragment {
        return items[position]?.let { CarouselFragment.newInstance(it) } as Fragment
    }
}