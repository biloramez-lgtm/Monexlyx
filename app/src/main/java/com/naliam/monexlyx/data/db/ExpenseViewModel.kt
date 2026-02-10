package com.naliam.monexlyx.data.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.naliam.monexlyx.data.AppDatabase
import com.naliam.monexlyx.data.entity.ExpenseEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val expenseDao =
        AppDatabase.getDatabase(application).expenseDao()

    // 📋 كل العمليات
    val expenses = expenseDao
        .getAllExpenses()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // 💰 مجموع الدخل
    val totalIncome = expenseDao
        .getTotalIncome()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0.0
        )

    // 💸 مجموع المصروف
    val totalExpense = expenseDao
        .getTotalExpense()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0.0
        )

    // ➕ إضافة عملية
    fun addExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            expenseDao.insertExpense(expense)
        }
    }

    // 🧹 حذف الكل (اختياري)
    fun clearAll() {
        viewModelScope.launch {
            expenseDao.clearAll()
        }
    }
}
