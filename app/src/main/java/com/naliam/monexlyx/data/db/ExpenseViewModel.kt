package com.naliam.monexlyx.data.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.naliam.monexlyx.data.AppDatabase
import com.naliam.monexlyx.data.entity.ExpenseEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase
        .getDatabase(application)
        .expenseDao()

    /* =========================
       📋 StateFlow للـ Compose
       ========================= */

    val allExpenses: StateFlow<List<ExpenseEntity>> =
        dao.getAllExpenses()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    val totalIncome: StateFlow<Double> =
        dao.getTotalIncome()
            .map { it ?: 0.0 }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = 0.0
            )

    val totalExpense: StateFlow<Double> =
        dao.getTotalExpense()
            .map { it ?: 0.0 }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = 0.0
            )

    /* =========================
       ➕ إضافة عمليات
       ========================= */

    fun addExpense(amount: Double, note: String) {
        if (amount <= 0) return
        addTransaction(amount, note, TransactionType.EXPENSE)
    }

    fun addIncome(amount: Double, note: String) {
        if (amount <= 0) return
        addTransaction(amount, note, TransactionType.INCOME)
    }

    /* =========================
       🔧 داخلية
       ========================= */

    private fun addTransaction(
        amount: Double,
        note: String,
        type: TransactionType
    ) {
        viewModelScope.launch {
            dao.insertExpense(
                ExpenseEntity(
                    amount = amount,
                    note = note.ifBlank { "" }, // ✅ String وليس String?
                    type = type.value,
                    date = System.currentTimeMillis()
                )
            )
        }
    }
}

/* =========================
   🏷 نوع العملية
   ========================= */

enum class TransactionType(val value: String) {
    INCOME("income"),
    EXPENSE("expense")
}
