package lt.boldadmin.nexus.google.maps.plugin.geocode

import com.google.maps.GeocodingApi
import com.google.maps.model.GeocodingResult
import com.google.maps.model.LatLng
import lt.boldadmin.nexus.google.maps.plugin.createApiContext

object ReverseGeocoder {

    internal fun geocode(latitude: Double, longitude: Double): Array<GeocodingResult> =
        GeocodingApi.reverseGeocode(
            createApiContext(),
            LatLng(latitude, longitude)
        ).await()

}
