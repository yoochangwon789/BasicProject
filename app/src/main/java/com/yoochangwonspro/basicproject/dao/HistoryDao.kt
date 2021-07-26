package com.yoochangwonspro.basicproject.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yoochangwonspro.basicproject.model.History

// room 에 연결된 dao
// History 에서 정의 된 객체를 어떻게 사용할 것인지 명시하는 인터페이스
@Dao
interface HistoryDao {

    // History 의 모든 데이터를 가져오는 함수
    @Query("SELECT * From history")
    fun getAll(): List<History>

    @Insert
    fun insertHistory(history: History)

    @Query("DELETE From history")
    fun deleteAll()

    // 하나의 데이터만 지울 때
    @Delete
    fun delete(history: History)

    // 조건의 부합하는 데이터를 가져올 때 는 LIKE 문까지
    // 한개만 가져올 꺼면 LIMIT 문까지
    @Query("SELECT * FROM history WHERE result LIKE :result LIMIT 1")
    fun findByResult(result: String): List<History>
}