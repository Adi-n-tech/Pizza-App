package com.app.pizzaapp.utils.network

import androidx.lifecycle.MutableLiveData
import retrofit2.Response

suspend fun <T> handleApiResponse(
    call: suspend () -> Response<T>,
    resultLiveData: MutableLiveData<NetworkResult<T>>
) {
    resultLiveData.postValue(NetworkResult.Loading())
    try {
        val response = call.invoke()
        if (response.isSuccessful && response.body() != null) {
            resultLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            resultLiveData.postValue(NetworkResult.Error(response.errorBody().toString()))
        } else {
            resultLiveData.postValue(NetworkResult.Error("Something went wrong !!"))
        }
    } catch (e: Exception) {
        resultLiveData.postValue(NetworkResult.Error(e.message ?: "Something went wrong !!"))
    }
}
