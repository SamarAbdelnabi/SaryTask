package com.android.sary.ui.storeFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.android.sary.common.utils.onClick
import com.android.sary.data.entity.ResultResponse
import com.android.sary.databinding.BannerItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class BannerAdapter (
    private val context: Context,
    private val itemList: List<ResultResponse>,
    private val layoutInflater: LayoutInflater,
    private val onItemClicked: (ResultResponse) -> Unit
) : PagerAdapter() {

    private lateinit var binding: BannerItemBinding

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        binding = BannerItemBinding.inflate(layoutInflater)
        val ad = itemList[position]

        Glide.with(context)
            .load(ad.image)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imgMedia)

        binding.imgMedia.onClick {
            onItemClicked.invoke(itemList[position])
        }

        (container as ViewPager).addView(binding.root, 0)
        return binding.root
    }


    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun getCount(): Int {
        return itemList.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        `object` as View
        (container as ViewPager).removeView(`object`)
    }
}