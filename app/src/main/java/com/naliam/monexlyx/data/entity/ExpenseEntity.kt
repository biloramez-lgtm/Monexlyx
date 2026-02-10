package com.naliam.monexlyx.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // المبلغ
    val amount: Double,

    // نوع العملية: "expense" أو "income"
    val type: String,

    // وصف اختياري (مثلاً: أكل، مواصلات...)
    val note: String,

    // التاريخ (timestamp)
    val date: Long = System.currentTimeMillis()
)
