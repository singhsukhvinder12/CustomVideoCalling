package com.customvideocalling.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.customvideocalling.views.fragment.JobRequestsFragment
import com.customvideocalling.views.fragment.StudentHistoryFragment
import com.customvideocalling.views.fragment.TeacherLiveFragment

class MyViewTeacherPagerAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                //  val homeFragment: HomeFragment = HomeFragment()
                return TeacherLiveFragment()
            }
            1 -> {
                return StudentHistoryFragment()//TODO
            }

            else -> return TeacherLiveFragment()
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}