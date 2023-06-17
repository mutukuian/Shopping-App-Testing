package com.example.shoppinglisttesting.repositories

import androidx.lifecycle.LiveData
import com.example.shoppinglisttesting.data.local.ShoppingItem
import com.example.shoppinglisttesting.data.remote.responses.ImageResponse
import com.example.shoppinglisttesting.utils.Resource


interface RepoInterface {


    suspend fun insertShoppingItems(shoppingItem: ShoppingItem);

    suspend fun deleteShoppingItems(shoppingItem: ShoppingItem);

    fun observeShoppingItems():LiveData<List<ShoppingItem>>

    fun observeTotalPrice():LiveData<Float>

    suspend fun searchImage(imageQuery:String): Resource<ImageResponse>
}