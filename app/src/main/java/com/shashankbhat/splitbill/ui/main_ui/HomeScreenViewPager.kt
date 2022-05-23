package com.shashankbhat.splitbill.ui.main_ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.shashankbhat.splitbill.R
import com.shashankbhat.splitbill.database.local.dto.group_list.GroupListDto
import com.shashankbhat.splitbill.databinding.FragmentBillShareViewPagerBinding
import com.shashankbhat.splitbill.databinding.FragmentHomeScreenViewPagerBinding
import com.shashankbhat.splitbill.ui.bill_shares.add_bill.AddBillSharesBottomSheetFragment
import com.shashankbhat.splitbill.ui.bill_shares.balance.ShowBillSharesBottomSheetFragment
import com.shashankbhat.splitbill.ui.bill_shares.shares.BillShareFragment
import com.shashankbhat.splitbill.ui.main_ui.group_list.GroupListFragment
import com.shashankbhat.splitbill.ui.main_ui.nearby_people.NearbyPeopleFragment
import com.shashankbhat.splitbill.ui.main_ui.profile.ProfileFragment
import com.shashankbhat.splitbill.util.ViewPagerAdapter
import com.shashankbhat.splitbill.viewmodels.BillShareViewModel
import com.shashankbhat.splitbill.viewmodels.GroupListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenViewPager : Fragment() {

    private lateinit var binding: FragmentHomeScreenViewPagerBinding
    private val viewModel: GroupListViewModel by activityViewModels()

    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenViewPagerBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val groupsFragment = GroupListFragment.getInstance()
        val peopleFragment = NearbyPeopleFragment.getInstance()
        val profileFragment = ProfileFragment.getInstance()

        val adapterFragments = arrayListOf<Fragment>()
        adapterFragments.add(groupsFragment)
        adapterFragments.add(peopleFragment)
        adapterFragments.add(profileFragment)

        binding.vpBillShares.adapter = ViewPagerAdapter(requireActivity(), adapterFragments)

//        binding.fab.setOnClickListener {
//            showAddBillBottomSheet()
//        }

        binding.vpBillShares.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position){
                    0 -> binding.bottomNavigation.selectedItemId = R.id.menu_groups
                    2 -> binding.bottomNavigation.selectedItemId = R.id.menu_profile
                    else -> {
                        binding.bottomNavigation.selectedItemId = R.id.menu_people
                    }
                }
            }
        })

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_groups -> binding.vpBillShares.setCurrentItem(0, true)
                R.id.menu_people -> binding.vpBillShares.setCurrentItem(1, true)
                R.id.menu_profile -> binding.vpBillShares.setCurrentItem(2, true)
            }
            return@setOnItemSelectedListener true
        }

    }

//    private fun showAddBillBottomSheet(){
//        val addBillDialog = AddBillSharesBottomSheetFragment(
//            groupListDto = groupListDto,
//            viewModel = viewModel
//        )
//        addBillDialog.show(parentFragmentManager, addBillDialog.tag)
//    }

}