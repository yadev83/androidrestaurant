package fr.isen.attia.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CarouselAdapter(activity: AppCompatActivity, private val items: List<String>) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return items.count()
    }

    override fun createFragment(position: Int): Fragment {
        return CarouselFragment.newInstance(items[position])
    }
}