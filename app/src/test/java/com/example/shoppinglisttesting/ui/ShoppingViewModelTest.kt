package com.example.shoppinglisttesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.shoppinglisttesting.MainCoroutineRule
import com.example.shoppinglisttesting.getOrAwaitValue

import com.example.shoppinglisttesting.repositories.FakeShoppingRepository
import com.example.shoppinglisttesting.utils.Constants
import com.example.shoppinglisttesting.utils.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ShoppingViewModelTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ShoppingViewModel

    @Before
    fun setup(){
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `insert shopping item with empty field, returns error`(){
        viewModel.insertShoppingItem("name","","3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()
            assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Resource.Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long name, returns error`(){
        val string = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1){
                append(1)
            }
        }
        viewModel.insertShoppingItem(string,"5","3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Resource.Status.ERROR)
    }

    @Test
    fun `insert price with too long price, returns error`(){
        val string = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH + 1){
                append(1)
            }
        }
        viewModel.insertShoppingItem("name","5",string)

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Resource.Status.ERROR)
    }

    @Test
    fun `insert shopping item with too high amount, returns error`(){

        viewModel.insertShoppingItem("name","555666666678987667832545","3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Resource.Status.ERROR)
    }

    @Test
    fun `insert shopping item with valid input, returns success`(){

        viewModel.insertShoppingItem("name","5","3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Resource.Status.SUCCESS)
    }
}