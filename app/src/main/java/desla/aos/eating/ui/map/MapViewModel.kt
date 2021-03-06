package desla.aos.eating.ui.map

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import desla.aos.eating.MyApplication
import desla.aos.eating.data.model.MapSearch
import desla.aos.eating.data.repository.MapRepository
import desla.aos.eating.ui.base.BaseViewModel
import desla.aos.eating.util.getActivity
import desla.aos.eating.util.hideKeyboard
import desla.aos.eating.util.startGPSSettingActivityForResult
import desla.aos.eating.util.startMainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.daum.mf.map.api.MapView


class MapViewModel(val repository: MapRepository) : BaseViewModel() {

    private val TAG = "MapViewModel"

    lateinit var mapView: MapView

    private val GPS_ENABLE_REQUEST_CODE = 2001
    private val PERMISSIONS_REQUEST_CODE = 2002
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

    var searchText = ""

    var isRegister = false


    private val _selectLocation = MutableLiveData<MapSearch>()
    val selectLocation : LiveData<MapSearch>
        get() = _selectLocation

    fun setSelectLocation(data: MapSearch){
        _selectLocation.value = data
    }


    fun initMap(){


        if (!checkLocationServicesStatus(mapView.context)) {
            showDialogForLocationServiceSetting(mapView.context)
        }else {
            checkRunTimePermission(mapView.context)
        }

    }


    fun sendRegister(v: View){

        if(isRegister){
            Log.i("eunjin", "??????" )

            val params:HashMap<String, Any> = HashMap<String, Any>()
            params["kakaoId"] = MyApplication.prefs.getString("id", "id")
            params["nickname"] = MyApplication.prefs.getString("name", "name")
            params["address"] = selectLocation.value?.Address!!
            params["latitude"] = selectLocation.value?.x!!
            params["longitude"] = selectLocation.value?.y!!

            Log.i("eunjin", "${params}")

            val disposable = repository.postRegister(
                    params
            )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if(it.isSuccessful){
                            if(it.body()?.status == "OK"){

                                MyApplication.prefs.setUserLocation(selectLocation.value?.Address!!,
                                        selectLocation.value?.x!!.toString(), selectLocation.value?.y!!.toString())

                                v.context.getActivity()?.startMainActivity()
                                v.context.getActivity()?.finish()

                            }else{
                                Log.i("eunjin", "?????? ??????1 ${it.body()?.status} ${it.body()?.message} ${params["nickname"]}" )
                            }

                        }else{
                            Log.i("eunjin", "?????? ??????2 ${it.code().toString()}  ${it.body()?.message}  ${it.body()?.status}" )
                        }

                    }, {

                        Log.i("eunjin", "?????? ??????3  ${it.localizedMessage} ????????? ${it.localizedMessage}" )
                    })
            addDisposable(disposable)
        }else{


            modifyAddress(v)


        }



    }


    private fun modifyAddress(v: View){
        Log.i("eunjin", "??????" )

        val params:HashMap<String, Any> = HashMap<String, Any>()
        params["address"] = selectLocation.value?.Address!!
        params["latitude"] = selectLocation.value?.x!!
        params["longitude"] = selectLocation.value?.y!!

        Log.i("eunjin", "${params}")

        val disposable = repository.modifyAdress(
            params
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.isSuccessful){
                    if(it.body()?.status == "OK"){

                        MyApplication.prefs.setUserLocation(selectLocation.value?.Address!!,
                            selectLocation.value?.x!!.toString(), selectLocation.value?.y!!.toString())
                        v.context.getActivity()?.finish()

                    }else{
                        Log.i("eunjin", "?????? ?????? ??????1 ${it.body()?.status} ${it.body()?.message} ${params["nickname"]}" )
                    }

                }else{
                    Log.i("eunjin", "?????? ?????? ??????2 ${it.code()} ${it.body()?.message}" )
                }

            }, {

                Log.i("eunjin", "?????? ?????? ??????3  ${it.localizedMessage} ????????? ${it.localizedMessage}" )
            })
        addDisposable(disposable)
    }


    val clicksListener = object : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH  -> {
                    v?.hideKeyboard()
                    getLocationWithKeyword(searchText)
                }
                else -> return false
            }
            return true
        }

    }


    private val _locationList = MutableLiveData<List<MapSearch>>()
    val locationList : LiveData<List<MapSearch>>
        get() = _locationList

    //???????????? ??????
    private fun getLocationWithKeyword(query: String){
        val disposable = repository.getLocationWithKeyword(query ,
                "KakaoAK fc1e18a34b2c31ec6d45168ef4e15284")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    if(it.meta.totalCount > 0){
                        val list = mutableListOf<MapSearch>()
                        for(data in it.documents){
                            list.add(MapSearch(data.placeName, data.addressName, data.roadAddressName,
                                    data.x.toDouble(), data.y.toDouble()))
                        }
                        _locationList.value = list

                    }else{
                        getLocationList(query)
                    }

                }, {
                })
        addDisposable(disposable)
    }

    //????????? ??????
    private fun getLocationList(query: String){

        val disposable = repository.getAddressWithQuery(query ,
                "KakaoAK fc1e18a34b2c31ec6d45168ef4e15284")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    if(it.meta.totalCount > 0){
                        val list = mutableListOf<MapSearch>()
                        for(data in it.documents){
                            list.add(MapSearch(null, data.addressName, data.roadAddress.roadName,
                                    data.x.toDouble(), data.y.toDouble()))
                        }
                        _locationList.value = list
                    }else{
                        _message.value = "???????????? ????????? ????????????"
                    }

                }, {
                })
        addDisposable(disposable)

    }

    var x = 0.0
    var y = 0.0

    fun getAddressWithGeo(x: Double, y: Double){

        if(this.x == x && this.y == y) return

        this.x = x
        this.y = y

            val disposable = repository.getAddressWithGeo(y, x,
                    "KakaoAK fc1e18a34b2c31ec6d45168ef4e15284")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({

                        if(it.meta.totalCount > 0){

                            var road = ""
                            if(it.documents[0].roadAddress != null){
                                if(it.documents[0].roadAddress.roadName != null){
                                    road = it.documents[0].roadAddress.roadName
                                }
                            }

                            setSelectLocation(MapSearch("", it.documents[0].address.addressName, road ,
                                    y, x))
                        }else{
                            _message.value = "???????????? ????????? ????????????"
                        }

                    }, {

                        println(it.message)

                    })
            addDisposable(disposable)


    }





    private fun checkLocationServicesStatus(context: Context) : Boolean {

        val locationManager = context.getActivity()?.getSystemService(LOCATION_SERVICE) as LocationManager

        return  locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    }

    private fun showDialogForLocationServiceSetting(context: Context){

        val builder = AlertDialog.Builder(context)
        builder.setTitle("?????? ????????? ????????????")
        builder.setMessage("?????? ???????????? ???????????? ?????? ???????????? ???????????????.\n")
        builder.setCancelable(true)
        builder.setPositiveButton("??????", DialogInterface.OnClickListener { dialog, which ->

            val callGPSSettingIntent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            context.startGPSSettingActivityForResult()
        })
        builder.setNegativeButton("??????", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })
        builder.create().show()

    }

    private fun checkRunTimePermission(context: Context){

        //????????? ????????? ??????
        // 1. ?????? ???????????? ????????? ????????? ???????????????.
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)

        val activity = context.getActivity()!!

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {
            // 2. ?????? ???????????? ????????? ?????????
            // ( ??????????????? 6.0 ?????? ????????? ????????? ???????????? ???????????? ????????? ?????? ????????? ?????? ???????????????.)
            // 3.  ?????? ?????? ????????? ??? ??????

            getLocationData()


        } else {  //2. ????????? ????????? ????????? ?????? ????????? ????????? ????????? ???????????????. 2?????? ??????(3-1, 4-1)??? ????????????.
            // 3-1. ???????????? ????????? ????????? ??? ?????? ?????? ????????????
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, REQUIRED_PERMISSIONS[0])) {
                // 3-2. ????????? ???????????? ?????? ?????????????????? ???????????? ????????? ????????? ???????????? ????????? ????????????.
                _message.value =  "??? ?????? ??????????????? ?????? ?????? ????????? ???????????????."
                // 3-3. ??????????????? ????????? ????????? ?????????. ?????? ????????? onRequestPermissionResult?????? ???????????????.
                ActivityCompat.requestPermissions(activity, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            } else {
                // 4-1. ???????????? ????????? ????????? ??? ?????? ?????? ???????????? ????????? ????????? ?????? ?????????.
                // ?????? ????????? onRequestPermissionResult?????? ???????????????.
                ActivityCompat.requestPermissions(activity, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }


    }

    fun onRequestPermissionsResult(permsRequestCode: Int, permissions: Array<out kotlin.String>, grantResults: IntArray){
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grantResults.size == REQUIRED_PERMISSIONS.size) {

            // ?????? ????????? PERMISSIONS_REQUEST_CODE ??????, ????????? ????????? ???????????? ??????????????????
            var check_result = true

            // ?????? ???????????? ??????????????? ???????????????.
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false
                    break
                }
            }
            if (check_result) {
                Log.d("@@@", "start")
                //?????? ?????? ????????? ??? ??????

                getLocationData()


            } else {
                // ????????? ???????????? ????????? ?????? ????????? ??? ?????? ????????? ??????????????? ?????? ???????????????.2 ?????? ????????? ??????
                val activity = mapView.context.getActivity()!!

                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, REQUIRED_PERMISSIONS[0])) {
                    _message.value =  "???????????? ?????????????????????. ?????? ?????? ???????????? ???????????? ??????????????????."
                    activity.finish()
                } else {
                    _message.value =  "???????????? ?????????????????????. ??????(??? ??????)?????? ???????????? ???????????? ?????????."
                }
            }
        }
    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            GPS_ENABLE_REQUEST_CODE ->                 //???????????? GPS ?????? ???????????? ??????
                if (checkLocationServicesStatus(mapView.context)) {
                    if (checkLocationServicesStatus(mapView.context)) {
                        Log.d("@@@", "onActivityResult : GPS ????????? ?????????")
                        checkRunTimePermission(mapView.context)
                        return
                    }
                }
        }
    }


    private fun getLocationData(){
        Log.i("MapViewModle","??????")
        LocationHelper().startListeningUserLocation(mapView.context , object : LocationHelper.MyLocationListener {
            override fun onLocationChanged(location: Location) {
                // Here you got user location :)
                Log.i("MapViewModle","" + location.latitude + "," + location.longitude)
                getAddressWithGeo(location.latitude, location.longitude)
            }
        })

    }




}