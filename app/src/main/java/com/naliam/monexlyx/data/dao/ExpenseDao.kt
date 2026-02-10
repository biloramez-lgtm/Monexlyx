package com.naliam.monexlyx.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.naliam.monexlyx.data.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    // ➕ إضافة عملية (دخل أو مصروف)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    // 📋 جلب كل العمليات (مباشر مع Compose)
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    // 💰 مجموع الدخل
    @Query("SELECT IFNULL(SUM(amount), 0) FROM expenses WHERE type = 'income'")
    fun getTotalIncome(): Flow<Double>

    // 💸 مجموع المصروف
    @Query("SELECT IFNULL(SUM(amount), 0) FROM expenses WHERE type = 'expense'")
    fun getTotalExpense(): Flow<Double>

    // 🧹 حذف كل البيانات (اختياري لاحقاً)
    @Query("DELETE FROM expenses")
    suspend fun clearAll()
}
