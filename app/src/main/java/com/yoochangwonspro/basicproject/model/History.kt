package com.yoochangwonspro.basicproject.model

// DB Table 로 사용
data class History(
    val uid: Int?,                  // Primary key
    val expression: String?,        // 계산식
    val result: String?             // 결과
)