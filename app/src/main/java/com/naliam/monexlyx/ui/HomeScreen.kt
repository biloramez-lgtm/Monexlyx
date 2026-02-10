package com.naliam.monexlyx.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.naliam.monexlyx.data.db.ExpenseViewModel

@Composable
fun HomeScreen(
    expenseViewModel: ExpenseViewModel
) {
    val context = LocalContext.current

    // 🔗 Room flows
    val totalIncome by expenseViewModel.totalIncome.collectAsState(initial = 0.0)
    val totalExpense by expenseViewModel.totalExpense.collectAsState(initial = 0.0)
    val balance = totalIncome - totalExpense

    // 🔧 Dialog state
    var showDialog by remember { mutableStateOf(false) }
    var isIncome by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isIncome = false
                    showDialog = true
                }
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

            Text(
                text = "Monexlyx",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            // 💰 الرصيد
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

            // ⚡ أزرار سريعة
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OutlinedButton(
                    onClick = {
                        isIncome = false
                        showDialog = true
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("➕ مصروف")
                }

                OutlinedButton(
                    onClick = {
                        isIncome = true
                        showDialog = true
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("💾 دخل")
                }
            }
        }
    }

    // =========================
    // 💸 Dialog (دخل / مصروف)
    // =========================
    if (showDialog) {
        AddTransactionDialog(
            isIncome = isIncome,
            onDismiss = { showDialog = false },
            onSave = { amount, note ->
                val value = amount.toDoubleOrNull()

                if (value == null || value <= 0) {
                    Toast.makeText(
                        context,
                        "❌ أدخل مبلغ صحيح",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@AddTransactionDialog
                }

                if (isIncome) {
                    expenseViewModel.addIncome(value, note)
                    Toast.makeText(context, "✅ تم إضافة الدخل", Toast.LENGTH_SHORT).show()
                } else {
                    expenseViewModel.addExpense(value, note)
                    Toast.makeText(context, "✅ تم إضافة المصروف", Toast.LENGTH_SHORT).show()
                }

                showDialog = false
            }
        )
    }
}

@Composable
private fun AddTransactionDialog(
    isIncome: Boolean,
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
                enabled = amount.toDoubleOrNull()?.let { it > 0 } == true
            ) {
                Text("حفظ")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إلغاء")
            }
        },
        title = {
            Text(if (isIncome) "💾 إضافة دخل" else "➕ إضافة مصروف")
        },
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
