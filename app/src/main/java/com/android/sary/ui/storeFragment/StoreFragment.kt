package com.android.sary.ui.storeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.android.sary.common.ui.state.ViewState
import com.android.sary.common.utils.showToast
import com.android.sary.data.entity.ResultResponse
import com.android.sary.databinding.FragmentStoreBinding
import com.android.sary.ui.BaseFragment
import com.android.sary.ui.storeFragment.adapter.CatalogAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class StoreFragment: BaseFragment() {

    override val viewModel by viewModels<StoreViewModel>()
    private val binding get() = (_binding as FragmentStoreBinding)

    private lateinit var viewPagerAdapter: BannerAdapter
    private var pageScrollPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        viewModel.getBanners()
        viewModel.getCatalog()
    }

    private fun observeData() {
        viewModel.bannersListLiveData.observe(this, Observer {
            when (it) {
                is ViewState.Loading -> {
                }
                is ViewState.Success -> {
                    setupViewPager(it.data)
                }
                else -> {
                }
            }
        })

        viewModel.catalogListLiveData.observe(this, Observer {
            when (it) {
                is ViewState.Loading -> {
                }
                is ViewState.Success -> {
                    binding.catalogRv.adapter = CatalogAdapter(
                        requireContext(),
                        it.data.result
                    ) {
                        showToast(requireContext(), it.name.orEmpty())
                    }
                    binding.catalogRv.clipChildren = false

                }
                else -> {
                }
            }
        })
    }

    private fun setupViewPager(result: List<ResultResponse>) {
        viewPagerAdapter = BannerAdapter(
            requireContext(),
            result,
            LayoutInflater.from(requireContext())
        ) { response ->
            showToast(requireContext(), response.link.orEmpty())
        }
        binding.viewPager.adapter = viewPagerAdapter
        if (result.size > 1) {
            autoScrollPager()
            binding.indicator.setViewPager(binding.viewPager)
            binding.indicator.visibility = View.VISIBLE
        } else {
            binding.indicator.visibility = View.GONE
        }
        binding.viewPager.clipChildren = false
    }

    private fun autoScrollPager() {

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                pageScrollPosition = position + 1
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
        })

        lifecycleScope.launchWhenResumed {
            while (true) {
                delay(3000)
                val count = viewPagerAdapter.count ?: 0
                try {
                    binding.viewPager.setCurrentItem(pageScrollPosition++ % count, true)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }
}