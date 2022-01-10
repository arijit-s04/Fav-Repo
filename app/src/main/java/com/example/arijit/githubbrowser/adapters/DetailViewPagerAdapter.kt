package com.example.arijit.githubbrowser.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.arijit.githubbrowser.fragments.BranchFragment
import com.example.arijit.githubbrowser.fragments.IssueFragment

val TAB_TITLES = listOf("Branches", "Issues")

class DetailViewPagerAdapter(
    fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence {
        return if (position < 0 || position >= 3) ""
            else TAB_TITLES[position]
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> BranchFragment()
            else -> IssueFragment()
        }
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }

}