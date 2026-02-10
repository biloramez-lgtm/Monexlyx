package com.naliam.monexlyx.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.naliam.monexlyx.data.db.ExpenseViewModel

@Composable
fun StatsScreen(
    expenseViewModel: ExpenseViewModel
) {
    val totalIncome by expenseViewModel.totalIncome.collectAsState(initial = 0.0)
    val totalExpense by expenseViewModel.totalExpense.collectAsState(initial = 0.0)

    val balance = totalIncome - totalExpense
    val maxValue = maxOf(totalIncome, totalExpense, 1.0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = "الإحصائيات",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        // 💾 الدخل
        StatCard(
            title = "مجموع الدخل",
            value = "${totalIncome.toInt()} $",
            icon = Icons.Default.ArrowUpward,
            color = MaterialTheme.colorScheme.primaryContainer
        )

        // 💸 المصروف
        StatCard(
            title = "مجموع المصروف",
            value = "${totalExpense.toInt()} $",
            icon = Icons.Default.ArrowDownward,
            color = MaterialTheme.colorScheme.errorContainer
        )

        // 💰 الرصيد
        StatCard(
            title = "الرصيد",
            value = "${balance.toInt()} $",
            icon = Icons.Default.AccountBalance,
            color = MaterialTheme.colorScheme.secondaryContainer
        )

        // 📊 رسم الأعمدة
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("📊 مقارنة الدخل والمصروف", fontWeight = FontWeight.Medium)

                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                ) {
                    val barWidth = size.width / 4

                    val incomeHeight =
                        (totalIncome / maxValue * size.height).toFloat()
                    val expenseHeight =
                        (totalExpense / maxValue * size.height).toFloat()

                    drawRect(
                        color = Color(0xFF4CAF50),
                        topLeft = Offset(
                            x = size.width / 4 - barWidth / 2,
                            y = size.height - incomeHeight
                        ),
                        size = androidx.compose.ui.geometry.Size(
                            barWidth,
                            incomeHeight
                        )
                    )

                    drawRect(
                        color = Color(0xFFF44336),
                        topLeft = Offset(
                            x = size.width * 3 / 4 - barWidth / 2,
                            y = size.height - expenseHeight
                        ),
                        size = androidx.compose.ui.geometry.Size(
                            barWidth,
                            expenseHeight
                        )
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text("الدخل", color = Color(0xFF4CAF50))
                    Text("المصروف", color = Color(0xFFF44336))
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(title, fontWeight = FontWeight.Medium)
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
