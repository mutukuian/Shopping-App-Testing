package com.example.shoppinglisttesting.utils

open class Event<out T>(private val content: T) {
    var hasBeenHandled = false
    private set //allow external read but not write

    /**
     * returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T?{
        return if (hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content,even if it's already been handled.
     */

    fun peekContent(): T = content
}