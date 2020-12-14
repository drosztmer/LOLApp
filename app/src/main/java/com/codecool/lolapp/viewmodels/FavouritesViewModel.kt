package com.codecool.lolapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codecool.lolapp.model.data.Favourite
import com.codecool.lolapp.model.data.FavouriteDao
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FavouritesViewModel(private val dataSource: FavouriteDao) :ViewModel() {

    private val disposable = CompositeDisposable()
    val favourites = MutableLiveData<List<Favourite>>()
    val characterLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val deleteSuccess = MutableLiveData<Boolean>()
    val deleteFailure = MutableLiveData<Boolean>()
    val restored = MutableLiveData<Boolean>()

    init {
        getFavourites()
    }

    fun refresh() {
        getFavourites()
    }

    fun getFavourites() {
        loading.value = true
        disposable.add(
            dataSource.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Favourite>>() {
                    override fun onSuccess(value: List<Favourite>) {
                        favourites.value = value
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

    fun deleteFromFavourites(id: String) {
        deleteSuccess.value = false
        deleteFailure.value = false
        disposable.add(
            Completable.fromCallable {
                dataSource.deleteFavourite(id)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableCompletableObserver() {
                    override fun onComplete() {
                        deleteSuccess.value = true
                    }

                    override fun onError(e: Throwable) {
                        println(e.message)
                        deleteFailure.value = true
                    }

                })
        )
    }

    fun addToFavourites(favourite: Favourite) {
        restored.value = false
        disposable.add(
            dataSource.insertFavourite(favourite)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableCompletableObserver() {
                    override fun onComplete() {
                        restored.value = true
                        getFavourites()
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