package fr.isen.attia.androidrestaurant.carousel

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CarouselAdapter(activity: AppCompatActivity, private val items: List<String?>) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return items.count()
    }

    override fun createFragment(position: Int): Fragment {
        return items[position]?.let { CarouselFragment.newInstance(it) } as Fragment
    }
}