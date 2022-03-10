package com.kanika.onboardingscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.kanika.onboardingscreen.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.viewPager.adapter = ViewAdapter(this, this)
        binding.viewPager.offscreenPageLimit = 1
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 2) {
                    binding.btnNextStep.text = getText(R.string.finish)
                } else {
                    binding.btnNextStep.text = getText(R.string.next)
                }
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(arg0: Int) {}
        })
        TabLayoutMediator(binding.pageIndicator, binding.viewPager) { _, _ -> }.attach()

        binding.btnNextStep.setOnClickListener {
            if (getItem() > binding.viewPager.childCount) {
                val intent =
                    Intent(applicationContext, WelcomeActivity::class.java)
                startActivity(intent)
            } else {
                binding.viewPager.setCurrentItem(getItem() + 1, true)
            }
        }

        binding.btnPreviousStep.setOnClickListener {
            if (getItem() == 0) {
                finish()
            } else {
                binding.viewPager.setCurrentItem(getItem() - 1, true)
            }
        }
    }

    private fun getItem(): Int {
        return binding.viewPager.currentItem
    }
}