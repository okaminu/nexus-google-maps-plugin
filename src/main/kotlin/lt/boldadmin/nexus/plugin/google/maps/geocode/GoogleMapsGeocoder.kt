package lt.boldadmin.nexus.plugin.google.maps.geocode

import com.google.maps.GeocodingApi
import com.google.maps.model.GeocodingResult
import lt.boldadmin.nexus.plugin.google.maps.createApiContext

object GoogleMapsGeocoder {

    internal fun geocode(address: String): Array<GeocodingResult> =
        GeocodingApi.geocode(createApiContext(), address).await()

}
