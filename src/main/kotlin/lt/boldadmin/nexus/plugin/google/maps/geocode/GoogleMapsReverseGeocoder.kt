package lt.boldadmin.nexus.plugin.google.maps.geocode

import com.google.maps.GeocodingApi
import com.google.maps.model.GeocodingResult
import com.google.maps.model.LatLng
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import lt.boldadmin.nexus.plugin.google.maps.createApiContext

object GoogleMapsReverseGeocoder {

    internal fun geocode(coordinates: Coordinates): Array<GeocodingResult> =
        GeocodingApi.reverseGeocode(
            createApiContext(),
            LatLng(coordinates.latitude, coordinates.longitude)
        ).await()

}
