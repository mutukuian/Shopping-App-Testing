package com.example.shoppinglisttesting.utils


data class Resource<out T>(val status: Status, val data : T?= null, val message : String?= null){

    companion object{
        fun <T> loading() :Resource<T>{
            return Resource(Status.LOADING)
        }
        fun <T>success(data:T?) : Resource<T>{
            return Resource(Status.SUCCESS,data)
        }
        fun <T> error(error:String): Resource<T>{
            return Resource(Status.ERROR, message = error)
        }
    }

    enum class Status{
        LOADING,SUCCESS,ERROR
    }
}
