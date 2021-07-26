package com.yoochangwonspro.basicproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// DB Table 로 사용
@Entity
data class History(
    @PrimaryKey val uid: Int?,                                       // Primary key
    @ColumnInfo(name = "expression") val expression: String?,        // 계산식
    @ColumnInfo(name = "result") val result: String?                 // 결과
)