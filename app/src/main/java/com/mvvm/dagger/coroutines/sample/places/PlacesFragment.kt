package com.mvvm.dagger.coroutines.sample.places

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.mvvm.dagger.coroutines.sample.BR
import com.mvvm.dagger.coroutines.sample.R
import com.mvvm.dagger.coroutines.sample.base.BaseFragment
import com.mvvm.dagger.coroutines.sample.data.room.Place
import com.mvvm.dagger.coroutines.sample.databinding.FragmentPlacesBinding
import com.mvvm.dagger.coroutines.sample.livedata.Event
import com.mvvm.dagger.coroutines.sample.livedata.Status

class PlacesFragment: BaseFragment<PlacesViewModel,FragmentPlacesBinding>(), OnMapReadyCallback {

    companion object {
        private const val ZOOM = 4f
        const val TAG = "PlacesFragment"
        fun newInstance() = PlacesFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_places
    override fun getViewModelClass(): Class<PlacesViewModel> = PlacesViewModel::class.java
    override fun getVariablesToBind(): Map<Int, Any> = mapOf(
            BR.viewModel to viewModel
    )

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    lateinit var googleMap: GoogleMap

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var currentPlaces: List<Place> = emptyList()


    override fun initViewModel() {
        super.initViewModel()
        viewModel.places.observe(this,placesResponseObserver)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        super.initView(inflater, container)
        val mapFragment = childFragmentManager.findFragmentById(R.id.fragmentMapPlaces) as SupportMapFragment
        mapFragment.getMapAsync(this)
        dataBinding.placesFabRandom.setOnClickListener { fabView ->
            randomPlace()
            Snackbar.make(fabView, R.string.places_random_message, Snackbar.LENGTH_LONG).show()
        }
    }

    private val placesResponseObserver = Observer<Event<List<Place>>>{ onPlacesResponse(it) }

    override fun onMapReady(gm: GoogleMap) {
        googleMap = gm
        viewModel.getPlaces()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onPlacesResponse(response: Event<List<Place>>) {
        when(response.status) {
            Status.SUCCESS -> onPlacesSuccess(response.peekData() ?: emptyList())
            Status.FAILURE -> onPlacesFailure()
            Status.NETWORK_ERROR -> onNetworkError()
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onPlacesSuccess(places: List<Place>) {
        currentPlaces = places
        loadPlacesInMap()
        randomPlace()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onPlacesFailure() {
        showAlert(R.string.error_loading_places)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun loadPlacesInMap() {
        for (p in currentPlaces) {
            googleMap.addMarker(buildMarkerPlace(p))
        }
    }

    private fun buildMarkerPlace(p: Place): MarkerOptions {
        return MarkerOptions()
                .position(LatLng(p.lat, p.lon))
                .title(p.companyName)
                .snippet(p.address)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun randomPlace() {
        if (currentPlaces.isNotEmpty()) {
            val randomPosition = (0 until currentPlaces.size).shuffled().first()
            val place = currentPlaces[randomPosition]
            val cameraPosition = CameraPosition.Builder().target(LatLng(place.lat, place.lon)).zoom(ZOOM).build()
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

}