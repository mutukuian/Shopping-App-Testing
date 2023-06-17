package com.example.shoppinglisttesting.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglisttesting.data.local.ShoppingItem
import com.example.shoppinglisttesting.data.remote.responses.ImageResponse
import com.example.shoppinglisttesting.repositories.RepoInterface
import com.example.shoppinglisttesting.utils.Event
import com.example.shoppinglisttesting.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repoInterface: RepoInterface
):ViewModel() {

    val shoppingItems = repoInterface.observeShoppingItems()

    val totalPrice = repoInterface.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
        val images : LiveData<Event<Resource<ImageResponse>>> = _images

    private val _curImageUrl = MutableLiveData<String>()
    val curImageUrl : LiveData<String> = _curImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus : LiveData<Event<Resource<ShoppingItem>>> = _insertShoppingItemStatus


    fun setCurImageUrl(url:String){
        _curImageUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repoInterface.deleteShoppingItems(shoppingItem)
    }

    fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repoInterface.insertShoppingItems(shoppingItem)
    }

    fun insertShoppingItem(name:String,amountString:String,priceString:String){

    }

    fun searchForImage(imageQuery:String){

    }
}