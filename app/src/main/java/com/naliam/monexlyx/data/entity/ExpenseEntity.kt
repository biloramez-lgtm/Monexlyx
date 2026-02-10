package com.naliam.monexlyx.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    // 💰 المبلغ
    val amount: Double,

    // 🔖 نوع العملية: income / expense
    val type: String,

    // 📝 ملاحظة اختيارية
    val note: String? = null,

    // 🕒 التاريخ (Timestamp)
    val date: Long = System.currentTimeMillis()
)
