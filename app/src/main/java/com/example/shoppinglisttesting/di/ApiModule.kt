package com.example.shoppinglisttesting.di

import android.content.Context
import androidx.room.Room
import com.example.shoppinglisttesting.data.local.ShoppingDao
import com.example.shoppinglisttesting.data.local.ShoppingItemDatabase
import com.example.shoppinglisttesting.data.remote.PixabayApi
import com.example.shoppinglisttesting.repositories.DefaultRepository
import com.example.shoppinglisttesting.repositories.RepoInterface
import com.example.shoppinglisttesting.utils.Constants.BASE_URL
import com.example.shoppinglisttesting.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context,ShoppingItemDatabase::class.java,DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideDefaultShoppingRepository(
        dao:ShoppingDao,
        api: PixabayApi
    ) =DefaultRepository(api,dao) as RepoInterface

    @Provides
    @Singleton
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    )=database.shoppingDao()

    @Provides
    @Singleton
    fun providePixabayApi():PixabayApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayApi::class.java)
    }
}