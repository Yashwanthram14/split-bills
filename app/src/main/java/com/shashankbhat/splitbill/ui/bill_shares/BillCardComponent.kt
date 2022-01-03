package com.shashankbhat.splitbill.ui.bill_shares

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.shashankbhat.splitbill.model.BillDetailsModel
import com.shashankbhat.splitbill.model.BillListDto
import com.shashankbhat.splitbill.ui.theme.SplitBillTheme
import com.shashankbhat.splitbill.ui.theme.Typography

@ExperimentalMaterialApi
@Composable
fun BillCard(
    billListDto: List<BillListDto>? = listOf(
        BillListDto(
            BillDetailsModel(
                0,
                "SSS",
                0F,
                0,
                "SS",
                0F,
                0F,
                0
            )
        )
    )
) {

    Box(
        Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(8.dp),
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                val (tvBillName, tvTotalAmount, lcShare) = createRefs()

                Text(
                    text = billListDto?.get(0)?.billDetails?.bill_name ?: "",
                    modifier = Modifier
                        .constrainAs(tvBillName) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(tvTotalAmount.start)
                            width = Dimension.fillToConstraints
                        },
                    style = Typography.h6
                )

                Text(
                    text = "Total " + billListDto?.get(0)?.billDetails?.total_amount.toString(),
                    modifier = Modifier
                        .constrainAs(tvTotalAmount) {
                            top.linkTo(parent.top)
                            linkTo(tvBillName.end, parent.end, bias = 0F)
                            width = Dimension.wrapContent
                        },
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF2196F3)
                    )
                )

                LazyColumn(
                    modifier = Modifier
                        .heightIn(min = 50.dp, max = 700.dp)
                        .constrainAs(lcShare) {
                            top.linkTo(tvBillName.bottom)
                            start.linkTo(parent.start)
                        },
                ) {
                    items(billListDto ?: emptyList()) { bill ->
                        ConstraintLayout(
                            modifier = Modifier
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                                .fillMaxWidth()
                        ) {
                            val (tvName, tvSpent, clShareSpent) = createRefs()

                            Text(
                                text = bill.billDetails?.user_name ?: "",
                                style = Typography.body1,
                                modifier = Modifier
                                    .constrainAs(tvName) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                        width = Dimension.wrapContent
                                    }

                            )

                            if (bill.billDetails?.spent ?: 0F > 0)
                                Text(
                                    text = "Spent " + bill.billDetails?.spent.toString(),
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        letterSpacing = 0.4.sp,
                                        color = Color(0, 170, 91, 255)
                                    ),
                                    modifier = Modifier
                                        .constrainAs(tvSpent) {
                                            top.linkTo(tvName.bottom)
                                            start.linkTo(parent.start)
                                            width = Dimension.wrapContent
                                        }
                                )

                            Column(modifier = Modifier
                                .constrainAs(clShareSpent) {
                                    top.linkTo(parent.top)
                                    end.linkTo(parent.end)
                                    width = Dimension.wrapContent
                                }) {

                                Text(
                                    text = "Share " + bill.billDetails?.share.toString(),
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        letterSpacing = 0.4.sp
                                    ),
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }


                        }
                    }
                }

            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SplitBillTheme {
        BillCard()
    }
}