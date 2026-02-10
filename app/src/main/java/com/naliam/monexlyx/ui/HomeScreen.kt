package com.naliam.monexlyx.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO لاحقاً: إضافة عملية */ }
            ) {
                Icon(Icons.Default.Add, contentDescription = "إضافة عملية")
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // 🔷 عنوان التطبيق
            Text(
                text = "Monexlyx",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            // 💰 الرصيد الحالي
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(Modifier.padding(20.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Wallet, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("الرصيد الحالي", fontWeight = FontWeight.Medium)
                    }

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "0 $",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "آخر تحديث: اليوم",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // 🎯 هدف الادخار
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(20.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.TrendingUp, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("هدف الادخار", fontWeight = FontWeight.Medium)
                        }

                        Text("0 / 1000 $", style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(Modifier.height(12.dp))

                    LinearProgressIndicator(
                        progress = 0f,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                    )

                    Spacer(Modifier.height(8.dp))
                    Text("0% مكتمل", style = MaterialTheme.typography.bodyMedium)
                }
            }

            // 🎁 نقاط التحفيز
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(Modifier.padding(20.dp)) {

                    Text("🎁 نقاط التحفيز", fontWeight = FontWeight.Medium)

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "0 نقطة",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "سجّل عملياتك يوميًا لتحصل على نقاط",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            // ⚡ أزرار سريعة
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OutlinedButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("➕ مصروف")
                }

                OutlinedButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("💾 دخل")
                }
            }
        }
    }
}
