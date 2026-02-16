package com.example.betteryou.data.local.room.dao.user_product

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.betteryou.data.local.room.entity.user_products.UserProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: UserProductEntity)

    @Query("""
        SELECT * FROM user_daily_products 
        WHERE userId = :userId 
        AND date = :dayStart
    """)
    fun getProductsByUserAndDate(
        userId: String,
        dayStart: Long
    ): Flow<List<UserProductEntity>>

    @Query("""
        DELETE FROM user_daily_products 
        WHERE userId = :userId 
        AND date < :dayStart
    """)
    suspend fun clearPreviousDays(
        userId: String,
        dayStart: Long
    )

    @Query("""
    DELETE FROM user_daily_products 
    WHERE id = :id
    AND userId = :userId
""")
    suspend fun deleteProductById(
        id: Int,
        userId: String
    )
}