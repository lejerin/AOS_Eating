package desla.aos.eating.ui.map

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.R
import desla.aos.eating.data.network.KakaoApi
import desla.aos.eating.data.network.ServerApi
import desla.aos.eating.data.repository.MapRepository
import desla.aos.eating.databinding.ActivityMapBinding
import desla.aos.eating.ui.base.BaseActivity


class MapActivity : BaseActivity<ActivityMapBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_map

    private lateinit var factory: MapViewModelFactory
    private lateinit var viewModel: MapViewModel

    private val mapFragment: MapFragment = MapFragment()

    override fun initStartView() {



        val api = KakaoApi()
        val server = ServerApi()
        val repository = MapRepository(api,server)
        factory = MapViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(MapViewModel::class.java)


        intent.extras?.getBoolean("isRegister")?.let{
            viewModel.isRegister = it
        }

    }

    override fun initDataBinding() {


    }

    override fun initAfterBinding() {




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mapFragment.onActivityResult(requestCode, resultCode, data)
    }


}