package com.example.swuvoca.dic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.swuvoca.databinding.ActivityDicBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy


class DicActivity : AppCompatActivity() {
    lateinit var binding: ActivityDicBinding

    private val titles = arrayOf("단어 검색", "단어 추가")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        supportActionBar!!.elevation = 0f

        binding.viewPager.setAdapter(ViewPagerFragmentAdapter(this))

        TabLayoutMediator(binding.tabLayout, binding.viewPager,
            TabConfigurationStrategy { tab: TabLayout.Tab, position: Int -> tab.text = titles[position] }).attach()
    }


    inner class ViewPagerFragmentAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return SearchFragment()
                1 -> return AddFragment()
            }
            return SearchFragment()
        }

        override fun getItemCount(): Int {
            return titles.size
        }
    }

}