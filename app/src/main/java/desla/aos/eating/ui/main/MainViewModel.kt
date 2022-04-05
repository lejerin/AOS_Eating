package desla.aos.eating.ui.main

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageInfo
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import desla.aos.eating.R
import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.data.model.Review
import desla.aos.eating.data.model.User
import desla.aos.eating.data.model.UserResponse
import desla.aos.eating.data.repository.MainRepository
import desla.aos.eating.ui.base.BaseViewModel
import desla.aos.eating.util.getActivity
import desla.aos.eating.util.showNoFuncDoialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(
        private val repository: MainRepository
) : BaseViewModel() {

    fun showNoFunc(v: View){
        v.showNoFuncDoialog(v)
    }

    fun close(v: View){
        v.context.getActivity()?.onBackPressed()
    }

    fun goMail(v: View){
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "plain/Text"

        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("2021eating@gmail.com"))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "잇팅 문의 사항")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "앱 버전 (AppVersion):" + getVersionInfo(v.context.getActivity()!!) + "\n기기명 (Device):\n안드로이드 OS (Android OS):\n내용 (Content):\n")
        emailIntent.type = "message/rfc822"
        v.context.startActivity(emailIntent)
    }

    private fun getVersionInfo(a: Activity): String? {
        val info: PackageInfo = a.packageManager.getPackageInfo(a.packageName, 0)
        return info.versionName
    }


    private val _user = MutableLiveData<UserResponse.Data>()
    val user : LiveData<UserResponse.Data>
        get() = _user

    fun getUser(){


        val disposable = repository.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.isSuccessful){
                        if(it.body()?.status == "OK"){

                            _user.value = it.body()?.data

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

    private val _myList1 = MutableLiveData<List<PostsResponse.Data>>()
    val myList1 : LiveData<List<PostsResponse.Data>>
        get() = _myList1

    private val _myList2 = MutableLiveData<List<PostsResponse.Data>>()
    val myList2 : LiveData<List<PostsResponse.Data>>
        get() = _myList2

    fun getMyList(pos: Int){

        if(pos == 0) {
            //오픈 목록
            val disposable = repository.getPostsMine(
                    "0-1-2-3-4-5-6-7-8-9", 1000, 0, 30
            )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if(it.isSuccessful){
                            if(it.body()?.status == "OK"){
                                Log.i("eunjin", "글 불러오기 성공 ${it.code()} ${it.body()?.data?.size}" )

                                _myList1.value = it.body()?.data

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


        }else{
            val disposable = repository.getPostParticipated()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if(it.isSuccessful){
                            if(it.body()?.status == "OK"){

                                _myList2.value = it.body()?.data

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




    }

    private val _review = MutableLiveData<Review>()
    val review : LiveData<Review>
        get() = _review


    fun getReview(){


        val disposable = repository.getReview()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.isSuccessful){
                        if(it.body()?.status == "OK"){

                            it.body()?.let {data ->

                            _review.value = data

                        }


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

}