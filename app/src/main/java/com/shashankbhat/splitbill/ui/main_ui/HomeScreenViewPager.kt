package com.shashankbhat.splitbill.ui.main_ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.shashankbhat.splitbill.R
import com.shashankbhat.splitbill.databinding.FragmentHomeScreenViewPagerBinding
import com.shashankbhat.splitbill.ui.main_ui.group_list.GroupListFragment
import com.shashankbhat.splitbill.ui.main_ui.nearby_people.NearbyPeopleFragment
import com.shashankbhat.splitbill.ui.main_ui.profile.ProfileFragment
import com.shashankbhat.splitbill.ui.main_ui.profile.ProfileWithDataFragment
import com.shashankbhat.splitbill.util.ViewPagerAdapter
import com.shashankbhat.splitbill.util.extension.getFullName
import com.shashankbhat.splitbill.util.extension.getUniqueId
import com.shashankbhat.splitbill.viewmodels.GroupListViewModel

class HomeScreenViewPager : Fragment() {

    private lateinit var binding: FragmentHomeScreenViewPagerBinding
    private val viewModel: GroupListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenViewPagerBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.menu_unique_id).title = "# " + viewModel.sharedPreferences.getUniqueId()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.vpBillShares = binding.vpBillShares

        val groupsFragment = GroupListFragment.getInstance()
        val peopleFragment = NearbyPeopleFragment.getInstance()
        val profileFragment = ProfileFragment.getInstance()
        val profileWithDataFragment = ProfileWithDataFragment.getInstance()

        val adapterFragments = arrayListOf<Fragment>()
        adapterFragments.add(groupsFragment)
        adapterFragments.add(peopleFragment)

        if(viewModel.sharedPreferences.getFullName().isEmpty())
            adapterFragments.add(profileFragment)
        else
            adapterFragments.add(profileWithDataFragment)


        val adapter = ViewPagerAdapter(requireActivity(), adapterFragments)
        binding.vpBillShares.adapter = adapter

        binding.vpBillShares.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position){
                    0 ->binding.bottomNavigation.selectedItemId = R.id.menu_groups
                    2 -> {
                        binding.bottomNavigation.selectedItemId = R.id.menu_profile
                        viewModel.getProfile()
                    }
                    else ->  binding.bottomNavigation.selectedItemId = R.id.menu_people
                }
            }
        })

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_groups -> binding.vpBillShares.setCurrentItem(0, true)
                R.id.menu_people -> binding.vpBillShares.setCurrentItem(1, true)
                R.id.menu_profile -> {
                    viewModel.getProfile()
                    binding.vpBillShares.setCurrentItem(2, true)
                }
            }
            return@setOnItemSelectedListener true
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllGroups()
    }



}