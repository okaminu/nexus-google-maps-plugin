package lt.boldadmin.nexus.plugin.google.maps.geocode

import com.google.maps.model.GeocodingResult
import com.google.openlocationcode.OpenLocationCode
import lt.boldadmin.nexus.api.gateway.ReverseGeocoder
import lt.boldadmin.nexus.api.exception.LocationNotFoundException
import lt.boldadmin.nexus.api.type.valueobject.Coordinates

open class GoogleMapsReverseGeocoderAdapter(private val reverseGeocoder: GoogleMapsReverseGeocoder):
    GeocoderAdapter<Coordinates>(),
    ReverseGeocoder {

    override fun toAddress(coordinates: Coordinates): String = toGeocodingResult(coordinates).formattedAddress

    override fun toPlusCode(coordinates: Coordinates): String =
        OpenLocationCode(coordinates.latitude, coordinates.longitude).code

    override fun getGeocodeResults(location: Coordinates): Array<GeocodingResult> = reverseGeocoder.geocode(location)

    override fun getLocationNotFoundException(location: Coordinates): LocationNotFoundException =
        LocationNotFoundException("Location not found by coordinates: ${location.latitude} ${location.longitude}")

}
