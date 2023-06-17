package com.example.shoppinglisttesting.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglisttesting.data.local.ShoppingItem
import com.example.shoppinglisttesting.data.remote.responses.ImageResponse
import com.example.shoppinglisttesting.utils.Resource

class FakeShoppingRepository : RepoInterface{

    private val shoppingItems = mutableListOf<ShoppingItem>()

    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)

    private val observableTotalPrice = MutableLiveData<Float>()


    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value:Boolean){
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData(){
      observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice():Float{
        return shoppingItems.sumByDouble { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItems(shoppingItem: ShoppingItem) {
       shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItems(shoppingItem: ShoppingItem) {
       shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeShoppingItems(): LiveData<List<ShoppingItem>> {
     return observableShoppingItems
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchImage(imageQuery: String): Resource<ImageResponse> {
       return if (shouldReturnNetworkError){
           Resource.error("Error")
       }else{
           Resource.success(ImageResponse(listOf(),0,0))
       }
    }
}