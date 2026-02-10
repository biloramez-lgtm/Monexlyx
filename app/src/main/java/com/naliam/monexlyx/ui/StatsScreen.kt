package com.naliam.monexlyx.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun StatsScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        // 📊 العنوان
        Text(
            text = "الإحصائيات",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        // 💰 مجموع الدخل
        StatCard(
            title = "مجموع الدخل",
            value = "0 $",
            icon = Icons.Default.TrendingUp,
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )

        // 💸 مجموع المصروف
        StatCard(
            title = "مجموع المصروف",
            value = "0 $",
            icon = Icons.Default.TrendingDown,
            containerColor = MaterialTheme.colorScheme.errorContainer
        )

        // 📈 نسبة الادخار
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.PieChart, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("نسبة الادخار", fontWeight = FontWeight.Medium)
                }

                Spacer(Modifier.height(12.dp))

                LinearProgressIndicator(
                    progress = 0f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )

                Spacer(Modifier.height(8.dp))

                Text("0%", style = MaterialTheme.typography.bodyMedium)
            }
        }

        // 🚧 مخططات مستقبلية
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("📊 مخططات شهرية", fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "سيتم إضافة الرسوم البيانية قريبًا",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    containerColor: androidx.compose.ui.graphics.Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

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
