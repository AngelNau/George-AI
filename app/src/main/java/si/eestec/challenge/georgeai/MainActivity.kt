package si.eestec.challenge.georgeai

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.tabs.TabLayoutMediator
import si.eestec.challenge.georgeai.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    companion object {
        private const val TAG = "MainActivity"
        private const val NUM_OF_TABS = 3
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        val toolbar = _binding.toolbar
        setSupportActionBar(toolbar)

        configureTabLayout()
    }

    private fun configureTabLayout() {
        val tabLayout = _binding.tabLayout
        val viewPager = _binding.viewPager
        val tabPagerAdapter = TabPagerAdapter(this, NUM_OF_TABS)
        viewPager.adapter = tabPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Tasks"
//                1 -> tab.text = "Tab 2"
//                2 -> tab.text = "Tab 3"
            }
        }.attach()
    }
}