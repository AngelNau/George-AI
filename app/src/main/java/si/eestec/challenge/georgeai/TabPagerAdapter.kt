package si.eestec.challenge.georgeai

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabPagerAdapter(fa: FragmentActivity?, private val tabCounter: Int) : FragmentStateAdapter(fa!!) {
    override fun getItemCount(): Int {
        return tabCounter
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TaskFragment()
            1 -> GeorgeAssistantFragment()
//            2 -> Tab3Fragment()
            else -> {
                Fragment()
            }
        }
    }
}