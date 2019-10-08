package lt.boldadmin.nexus.plugin.google.maps.geocode

import com.google.maps.errors.ApiException
import com.google.maps.model.GeocodingResult
import com.google.maps.model.LatLng
import lt.boldadmin.nexus.api.Geocoder
import lt.boldadmin.nexus.api.exception.LocationNotFoundException
import lt.boldadmin.nexus.api.exception.ReverseGeocodeConverterException
import lt.boldadmin.nexus.api.type.valueobject.Coordinates

open class GoogleMapsGeocoderAdapter(private val geocoder: GoogleMapsGeocoder): GeocoderAdapter<String>(), Geocoder {

    override fun toCoordinates(address: String): Coordinates =
        try {
            toGeocodingResultWithoutExceptionHandling(address).geometry.location.toCoordinates()
        } catch (e: ApiException) {
            throw ReverseGeocodeConverterException("Api exception: ${e.message}")
        } catch (e: IllegalStateException) {
            throw ReverseGeocodeConverterException("Illegal state exception: ${e.message}")
        }

    override fun getGeocodeResults(location: String): Array<GeocodingResult> = geocoder.geocode(location)

    override fun getLocationNotFoundException(location: String): LocationNotFoundException =
        LocationNotFoundException("Location not found by address: $location")

    private fun LatLng.toCoordinates() = Coordinates(this.lat, this.lng)

}
