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

    // 📋 كل العمليات
    val allExpenses: Flow<List<ExpenseEntity>> =
        dao.getAllExpenses()

    // 💰 مجموع الدخل
    val totalIncome: Flow<Double> =
        dao.getTotalIncome()

    // 💸 مجموع المصروف
    val totalExpense: Flow<Double> =
        dao.getTotalExpense()

    // =========================
    // ➕ إضافة مصروف
    // =========================
    fun addExpense(amount: Double, note: String) {
        viewModelScope.launch {
            dao.insertExpense(
                ExpenseEntity(
                    amount = amount,
                    note = note,
                    type = "expense",
                    date = System.currentTimeMillis()
                )
            )
        }
    }

    // =========================
    // ➕ إضافة دخل
    // =========================
    fun addIncome(amount: Double, note: String) {
        viewModelScope.launch {
            dao.insertExpense(
                ExpenseEntity(
                    amount = amount,
                    note = note,
                    type = "income",
                    date = System.currentTimeMillis()
                )
            )
        }
    }
}
