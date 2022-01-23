package com.shashankbhat.splitbill.ui.user_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.shashankbhat.splitbill.ui.theme.SplitBillTheme
import com.shashankbhat.splitbill.viewmodels.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.shashankbhat.splitbill.R
import com.shashankbhat.splitbill.dto.group_list.GroupListDto
import com.shashankbhat.splitbill.util.component.BottomWarningText
import com.shashankbhat.splitbill.util.extension.findActivity

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private val viewModel: UserListViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var groupListDto: GroupListDto

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        navController = findNavController()

        groupListDto = requireArguments().getSerializable("model") as GroupListDto
        viewModel.getAllUsersByGroupId(groupListDto.group.id ?: -1)
        viewModel.getAllBill(groupListDto.group.id ?: -1)

        return ComposeView(requireContext()).apply {
            setContent {
                SplitBillTheme {
                    UserList()
                }
            }
        }
    }


    @Composable
    fun UserList() {

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            floatingActionButton = {
                if (viewModel.billList.value.isEmpty())
                    FloatingActionButton(
                        onClick = {
                            val addMember = AddGroupMemberFragment(viewModel, groupListDto)
                            context?.findActivity()?.supportFragmentManager?.let {
                                addMember.show(
                                    it,
                                    addMember.tag
                                )
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_outline_person_add),
                            contentDescription = "Add Member",
                            tint = Color.White
                        )
                    }
            },
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {

                val (lcGroup, ivNoData, tvNoData) = createRefs()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(lcGroup) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                ) {
                    items(viewModel.userListState.value.data ?: emptyList()) { user ->
                        UserCard(user = user, viewModel)
                    }
                }


                BottomWarningText(
                    modifier = Modifier
                        .constrainAs(ivNoData) {
                            bottom.linkTo(parent.bottom, margin = 70.dp)
                            end.linkTo(parent.end)
                        },
                    text = if (viewModel.billList.value.isEmpty())
                        "Note : You cannot add people after adding bills and shares to the group, So please make sure that you are adding all the people before adding any bill."
                    else "You cannot add users after adding bills and shares to group",
                    backgroundColor = if (viewModel.billList.value.isEmpty()) Color(0xFFA9B5FF) else Color(
                        0xFFFF8B9C
                    )
                )


                if (viewModel.userListState.value.data?.isEmpty() == true)
                    Box(modifier = Modifier
                        .constrainAs(tvNoData) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }) {
                        Text(
                            text = "No users found",
                            style = TextStyle(
                                fontFamily = FontFamily(
                                    Font(R.font.cabin_sketch, FontWeight.Normal),
                                ),
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                color = Color(0xFF818181)
                            )
                        )
                    }
            }

        }
    }

}