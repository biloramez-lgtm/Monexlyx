package com.naliam.monexlyx.data.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.naliam.monexlyx.data.AppDatabase
import com.naliam.monexlyx.data.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase
        .getDatabase(application)
        .expenseDao()

    /* =========================
       📋 البيانات
       ========================= */

    // كل العمليات
    val allExpenses: Flow<List<ExpenseEntity>> =
        dao.getAllExpenses()

    // مجموع الدخل
    val totalIncome: Flow<Double> =
        dao.getTotalIncome()

    // مجموع المصروف
    val totalExpense: Flow<Double> =
        dao.getTotalExpense()

    /* =========================
       ➕ إضافة عمليات
       ========================= */

    fun addExpense(amount: Double, note: String) {
        addTransaction(
            amount = amount,
            note = note,
            type = TransactionType.EXPENSE
        )
    }

    fun addIncome(amount: Double, note: String) {
        addTransaction(
            amount = amount,
            note = note,
            type = TransactionType.INCOME
        )
    }

    /* =========================
       🔧 Private helpers
       ========================= */

    private fun addTransaction(
        amount: Double,
        note: String,
        type: TransactionType
    ) {
        viewModelScope.launch {
            try {
                dao.insertExpense(
                    ExpenseEntity(
                        amount = amount,
                        note = note.ifBlank { null },
                        type = type.value,
                        date = System.currentTimeMillis()
                    )
                )
            } catch (e: Exception) {
                // لاحقاً ممكن تربطه Snackbar / Log / Crashlytics
                e.printStackTrace()
            }
        }
    }
}

/* =========================
   🏷 Transaction Type
   ========================= */

private enum class TransactionType(val value: String) {
    INCOME("income"),
    EXPENSE("expense")
}
