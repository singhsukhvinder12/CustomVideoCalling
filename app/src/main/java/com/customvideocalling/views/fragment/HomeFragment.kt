package com.customvideocalling.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.customvideocalling.R
import com.customvideocalling.databinding.FragmentHomeBinding
import com.customvideocalling.utils.core.BaseFragment


class HomeFragment : BaseFragment() {
    var binding:FragmentHomeBinding?=null
    override fun getLayoutResId(): Int {
       return R.layout.fragment_home
    }

    override fun initView() {
        binding=viewDataBinding as FragmentHomeBinding

    }
}