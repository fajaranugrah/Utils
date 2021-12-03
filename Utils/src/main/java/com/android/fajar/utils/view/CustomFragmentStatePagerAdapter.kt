package com.android.fajar.utils.view

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.lang.Exception

class CustomFragmentStatePagerAdapter(
    var mContext: Context,
    var fragmentManager: FragmentManager,
    var3: List<FragmentPagerItem?>?
) : FragmentStatePagerAdapter(
    fragmentManager
) {
    var fragmentPagerItemList: MutableList<FragmentPagerItem?> = mutableListOf()
    override fun getItem(var1: Int): Fragment {
        return fragmentPagerItemList[var1]!!.fragment
    }

    override fun getPageTitle(var1: Int): CharSequence? {
        return try {
            fragmentPagerItemList[var1]!!.title
        } catch (var3: Exception) {
            var3.printStackTrace()
            ""
        }
    }

    override fun getCount(): Int {
        return fragmentPagerItemList.size
    }

    init {
        fragmentPagerItemList.addAll(var3!!)
    }
}