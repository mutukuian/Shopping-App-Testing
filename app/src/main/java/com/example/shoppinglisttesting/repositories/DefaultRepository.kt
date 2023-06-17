package com.example.shoppinglisttesting.repositories


import androidx.lifecycle.LiveData

import com.example.shoppinglisttesting.data.local.ShoppingDao
import com.example.shoppinglisttesting.data.local.ShoppingItem
import com.example.shoppinglisttesting.data.remote.PixabayApi
import com.example.shoppinglisttesting.data.remote.responses.ImageResponse
import com.example.shoppinglisttesting.utils.Resource
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private  val pixabayApi: PixabayApi,
    private val dao: ShoppingDao
) :RepoInterface{

    override suspend fun insertShoppingItems(shoppingItem: ShoppingItem) {
        return dao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItems(shoppingItem: ShoppingItem) {
       return dao.deleteShoppingItem(shoppingItem)
    }

    override fun observeShoppingItems(): LiveData<List<ShoppingItem>> {
    return  dao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return dao.observeTotalPrice()
    }

    override suspend fun searchImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayApi.searchImage(imageQuery)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                }?: Resource.error("An unknown error occured")
            }else{
                Resource.error("An unknown error occurred")
            }
        }catch (e: Exception){
            Resource.error("Could not reach the server.Check your Internet connection")
        }
    }
}