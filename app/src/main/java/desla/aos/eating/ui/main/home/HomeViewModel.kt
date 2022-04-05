package desla.aos.eating.ui.main.home

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import desla.aos.eating.MyApplication
import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.data.repository.HomeRepository
import desla.aos.eating.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
        private val repository: HomeRepository
) : BaseViewModel() {


        private val _postList = MutableLiveData<List<PostsResponse.Data>>()
        val postList : LiveData<List<PostsResponse.Data>>
                get() = _postList


        private val _address = MutableLiveData<String>()
        val address : LiveData<String>
                get() = _address

        private val _distance = MutableLiveData<Int>()
        val distance : LiveData<Int>
                get() = _distance

        fun setAddress(str: String){
                _address.value = str
        }

        fun setDistance(dis: Int){
                _distance.value = dis
        }



        var category = MyApplication.prefs.getString("category", "0-1-2-3-4-5-6-7-8-9")
        var page = 0



        fun getPost(){


                val disposable = repository.getPosts(
                   category, if(_distance.value == null) 1000 else _distance.value!!, 0, 30
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                                if(it.isSuccessful){
                                        if(it.body()?.status == "OK"){
                                                Log.i("eunjin", "글 불러오기 성공 ${it.code()} ${it.body()?.data?.size}" )

                                                _postList.value = it.body()?.data

                                        }else{
                                                Log.i("eunjin", "글불러오기 실패 ${it.body()?.status} ${it.body()?.message} }" )
                                        }

                                }else{
                                        Log.i("eunjin", "${it.code()} 글 불러오기 실패2${it.body()?.status}  ${it.body()?.message}" )
                                }

                        }, {

                                Log.i("eunjin", "글 불러오기 실패3" )
                        })
                addDisposable(disposable)

        }

        fun goFavorite(isLike: Boolean, id: Int){

                if(!isLike){
                        //좋아요 취소
                        val disposable = repository.postUnFavorite(id)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                        if(it.isSuccessful){
                                                if(it.code() == 200){

                                                        Log.i("eunjin", "찜하기 취소 성공 ${it.code()}" )
                                                }else{
                                                        Log.i("eunjin", "찜하기 취소 실패 ${it.body()?.status} ${it.body()?.message} }" )
                                                }

                                        }else{
                                                Log.i("eunjin", "${it.code()} 찜하기 취소 실패2${it.body()?.status}  ${it.body()?.message}" )
                                        }

                                }, {

                                        Log.i("eunjin", "찜하기 취소 실패3" )
                                })
                        addDisposable(disposable)

                }else{
                        val disposable = repository.postFavorite(id)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                        if(it.isSuccessful){
                                                if(it.code() == 200){

                                                   //     _favorite.value = true

                                                        Log.i("eunjin", "찜하기 성공1 ${it.code()}" )
                                                }else{
                                                        Log.i("eunjin", "찜하기 실패2 ${it.body()?.status} ${it.body()?.message} }" )
                                                }

                                        }else{
                                                Log.i("eunjin", "${it.code()} 찜하기 실패3${it.body()?.status}  ${it.body()?.message}" )
                                        }

                                }, {

                                        Log.i("eunjin", "찜하기 실패4" )
                                })
                        addDisposable(disposable)
                }


        }

}