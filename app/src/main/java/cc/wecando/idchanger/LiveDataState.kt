package cc.wecando.idchanger


sealed class LiveDataState<out T> {
    data class Loading<out T>(val loadingMsg: T) : LiveDataState<Nothing>()
    data class Success<out T>(val data: T) : LiveDataState<T>()
    data class Error<T>(val errorMsg: T) : LiveDataState<Nothing>()
}
