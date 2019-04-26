package com.example.harry.customandroid.tabs.develop.coorinatorLayout

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.example.harry.customandroid.R
import com.example.harry.customandroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_coordinator_1.*

/**
 * Created by panqiang at 2019-04-22
 */
class CoordinatorLayoutActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_coordinator_1
    }

    override fun getTitleId(): Int {
        return R.string.coordinator_layout_title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private class HouseViewPagerAdapter : FragmentPagerAdapter {

        constructor(context: Context, fm: FragmentManager) : super(fm) {

        }

        override fun getItem(position: Int): Fragment {
            return RecycleViewFragment()
        }

        override fun getCount(): Int {
            return 1
        }

    }

    private class RecycleViewFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_recycler_view, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

        }
    }
}