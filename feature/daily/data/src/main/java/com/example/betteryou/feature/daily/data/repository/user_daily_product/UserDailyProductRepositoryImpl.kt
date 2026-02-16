package com.example.betteryou.feature.daily.data.repository.user_daily_product

import com.example.betteryou.data.local.room.dao.user_product.UserProductDao
import com.example.betteryou.data.local.room.entity.user_products.UserProductEntity
import com.example.betteryou.feature.daily.data.remote.mapper.toDomain
import com.example.betteryou.feature.daily.data.remote.mapper.toEntity
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.daily.domain.model.UserDailyProduct
import com.example.betteryou.feature.daily.domain.repository.user_daily_product.UserDailyProductRepository
import com.example.betteryou.util.getStartOfDayMillis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.util.Calendar
import javax.inject.Inject

class UserDailyProductRepositoryImpl @Inject constructor(
    private val dao: UserProductDao,
) : UserDailyProductRepository {

    override suspend fun addProduct(product: UserDailyProduct) {
        dao.insertProduct(product.toEntity())
    }

    override fun getTodayProducts(userId: String): Flow<Resource<List<UserDailyProduct>>> {
        return dao
            .getProductsByUserAndDate(userId, getStartOfDayMillis())
            .map<List<UserProductEntity>, Resource<List<UserDailyProduct>>> { entities ->
                Resource.Success(entities.map { it.toDomain() })
            }
            .onStart {
                emit(Resource.Loader(true))
            }
            .catch { e ->
                emit(Resource.Error(e.message ?: "Unknown error"))
            }
    }

    override suspend fun clearOldProducts(userId: String) {
        val todayStart = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        dao.deleteBefore(userId, todayStart)
    }

    override suspend fun deleteProductById(userId: String, id: Long) {
        dao.deleteProductById(id,userId)
    }
}