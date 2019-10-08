package lt.boldadmin.nexus.plugin.google.maps.geocode

import com.google.maps.model.GeocodingResult
import com.google.maps.model.LatLng
import lt.boldadmin.nexus.api.gateway.Geocoder
import lt.boldadmin.nexus.api.exception.LocationNotFoundException
import lt.boldadmin.nexus.api.type.valueobject.Coordinates

open class GoogleMapsGeocoderAdapter(private val geocoder: GoogleMapsGeocoder): GeocoderAdapter<String>(),
    Geocoder {

    override fun toCoordinates(address: String): Coordinates =
        toGeocodingResult(address).geometry.location.toCoordinates()

    override fun getGeocodeResults(location: String): Array<GeocodingResult> = geocoder.geocode(location)

    override fun getLocationNotFoundException(location: String): LocationNotFoundException =
        LocationNotFoundException("Location not found by address: $location")

    private fun LatLng.toCoordinates() = Coordinates(this.lat, this.lng)

}
