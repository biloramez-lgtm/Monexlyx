package com.naliam.monexlyx.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.naliam.monexlyx.data.db.ExpenseViewModel

@Composable
fun HomeScreen(
    expenseViewModel: ExpenseViewModel
) {
    // 🔗 Room flows
    val totalIncome by expenseViewModel.totalIncome.collectAsState(initial = 0.0)
    val totalExpense by expenseViewModel.totalExpense.collectAsState(initial = 0.0)

    val balance = totalIncome - totalExpense

    // 🔧 Dialog state
    var showAddExpenseDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddExpenseDialog = true }
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
                        text = "${balance.toInt()} $",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "الدخل: ${totalIncome.toInt()} $  •  المصروف: ${totalExpense.toInt()} $",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // 🎯 هدف الادخار (لسه ثابت)
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

            // 🎁 نقاط التحفيز (لسه ثابت)
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
                    onClick = { showAddExpenseDialog = true },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("➕ مصروف")
                }

                OutlinedButton(
                    onClick = { /* لاحقًا */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("💾 دخل")
                }
            }
        }
    }

    // =========================
    // 💸 Dialog إضافة مصروف (لسه بدون حفظ)
    // =========================
    if (showAddExpenseDialog) {
        AddExpenseDialog(
            onDismiss = { showAddExpenseDialog = false },
            onSave = { _, _ ->
                showAddExpenseDialog = false
            }
        )
    }
}

@Composable
private fun AddExpenseDialog(
    onDismiss: () -> Unit,
    onSave: (amount: String, note: String) -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = { onSave(amount, note) },
                enabled = amount.isNotBlank()
            ) {
                Text("حفظ")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إلغاء")
            }
        },
        title = { Text("➕ إضافة مصروف") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("المبلغ") },
                    keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("ملاحظة (اختياري)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}
