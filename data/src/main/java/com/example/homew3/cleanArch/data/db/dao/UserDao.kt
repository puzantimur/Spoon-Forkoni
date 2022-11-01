package com.example.homew3.cleanArch.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.homew3.cleanArch.data.model.entity.RecipesEntity
import com.example.homew3.cleanArch.data.model.entity.UserEntity
import com.example.homew3.domain.model.ExtendedIngredient
import com.example.homew3.domain.model.InfoRecipe
import com.example.homew3.domain.model.Recipe

@Dao
internal interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM userentity where login LIKE :login OR email LIKE :email LIMIT 1")
    suspend fun findByLoginOrEmail(login: String, email: String): UserEntity

    @Query("SELECT * FROM userentity where login LIKE :login and password LIKE :password")
    suspend fun loginUser(login: String, password: String): UserEntity

    @Query("SELECT * FROM userentity where id LIKE :id ")
    suspend fun getUserById(id: String): UserEntity

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("UPDATE userentity SET shopList = :list WHERE id LIKE :id")
    suspend fun updateShopList(list: List<ExtendedIngredient>, id: String)

    @Query("UPDATE userentity SET favorite = :list WHERE id LIKE :id")
    suspend fun updateFavorite(list: List<RecipesEntity>, id: String)


}
