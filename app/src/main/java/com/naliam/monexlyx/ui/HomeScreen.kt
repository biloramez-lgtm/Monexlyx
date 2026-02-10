package com.naliam.monexlyx.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.naliam.monexlyx.data.db.ExpenseViewModel
import com.naliam.monexlyx.data.entity.ExpenseEntity
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(
    expenseViewModel: ExpenseViewModel
) {
    val context = LocalContext.current

    val totalIncome by expenseViewModel.totalIncome.collectAsState()
    val totalExpense by expenseViewModel.totalExpense.collectAsState()
    val allExpenses by expenseViewModel.allExpenses.collectAsState()

    val balance = totalIncome - totalExpense

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
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Text(
                text = "Monexlyx",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(Modifier.padding(20.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AccountBalance, contentDescription = null)
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        isIncome = false
                        showDialog = true
                    }
                ) {
                    Text("➕ مصروف")
                }

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        isIncome = true
                        showDialog = true
                    }
                ) {
                    Text("💾 دخل")
                }
            }

            Text(
                text = "آخر العمليات",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            if (allExpenses.isEmpty()) {
                Text(
                    text = "لا توجد عمليات بعد",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(allExpenses.take(5)) { expense ->
                        TransactionItem(expense)
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddTransactionDialog(
            isIncome = isIncome,
            onDismiss = { showDialog = false },
            onSave = { amount, note ->
                val value = amount.toDoubleOrNull()

                if (value == null || value <= 0) {
                    Toast.makeText(context, "❌ أدخل مبلغ صحيح", Toast.LENGTH_SHORT).show()
                    return@AddTransactionDialog
                }

                if (isIncome) {
                    expenseViewModel.addIncome(value, note)
                } else {
                    expenseViewModel.addExpense(value, note)
                }

                showDialog = false
            }
        )
    }
}

@Composable
private fun TransactionItem(expense: ExpenseEntity) {
    val isIncome = expense.type == "income"
    val color =
        if (isIncome) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.error

    val date = remember(expense.date) {
        SimpleDateFormat("dd MMM", Locale.getDefault())
            .format(Date(expense.date))
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = if (isIncome) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                contentDescription = null,
                tint = color
            )

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    text = expense.note.ifBlank {
                        if (isIncome) "دخل" else "مصروف"
                    },
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = "${if (isIncome) "+" else "-"}${expense.amount.toInt()} $",
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
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
                    keyboardOptions = KeyboardOptions(
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
