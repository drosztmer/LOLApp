package com.codecool.lolapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codecool.lolapp.model.CharactersApi
import com.codecool.lolapp.model.ResponseCharacter
import com.codecool.lolapp.model.ResponseDetails
import com.codecool.lolapp.model.data.Favourite
import com.codecool.lolapp.model.data.FavouriteDao
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.function.Consumer

class DetailsViewModel(private var charactersApi: CharactersApi, private val dataSource: FavouriteDao) : ViewModel() {

    private var disposable = CompositeDisposable()
    val isFavourite = MutableLiveData<Boolean>()
    val response = MutableLiveData<ResponseDetails>()
    val characterLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(name: String) {
        getDetails(name)
        getFavourite(name)
    }

    fun getDetails(name: String) {
        loading.value = true
        disposable.add(
            charactersApi.getDetails(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseDetails>(){
                    override fun onSuccess(value: ResponseDetails) {
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

    fun getFavourite(id: String) {
        isFavourite.value = false
        disposable.add(
            dataSource.getFavouriteById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<Favourite>() {
                    override fun onSuccess(value: Favourite) {
                        isFavourite.value = true
                    }

                    override fun onError(e: Throwable) {
                        println(e.message)
                    }

                })
        )
    }

    fun addToFavourites(favourite: Favourite) {
        disposable.add(
            dataSource.insertFavourite(favourite)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableCompletableObserver() {
                    override fun onComplete() {
                        isFavourite.value = true
                    }

                    override fun onError(e: Throwable) {
                        println(e.message)
                    }

                })
        )
    }

    fun deleteFromFavourites(id: String) {
        disposable.add(
            Completable.fromCallable {
            dataSource.deleteFavourite(id)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableCompletableObserver() {
                    override fun onComplete() {
                        isFavourite.value = false
                    }

                    override fun onError(e: Throwable) {
                        println(e.message)
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}