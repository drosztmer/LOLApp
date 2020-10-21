package com.codecool.lolapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codecool.lolapp.model.Character
import com.codecool.lolapp.model.CharactersApi
import com.codecool.lolapp.model.ResponseCharacter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListingViewModel(private var charactersApi: CharactersApi) : ViewModel() {

    private var disposable = CompositeDisposable()
    val response = MutableLiveData<ResponseCharacter>()
    val characterLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    init {
        getCharacters()
    }

    fun refresh() {
        getCharacters()
    }

    fun getCharacters() {
        loading.value = true
        disposable.add(
            charactersApi.getCharacters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<ResponseCharacter>() {
                    override fun onSuccess(value: ResponseCharacter) {
                        response.value = value
                        characterLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        println(e.message)
                        characterLoadError.value = true
                        loading.value = false
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}