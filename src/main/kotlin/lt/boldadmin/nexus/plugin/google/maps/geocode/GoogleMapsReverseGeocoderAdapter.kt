package lt.boldadmin.nexus.plugin.google.maps.geocode

import com.google.maps.errors.ApiException
import com.google.openlocationcode.OpenLocationCode
import lt.boldadmin.nexus.api.exception.CoordinatesNotFoundException
import lt.boldadmin.nexus.api.exception.ReverseGeocoderException
import lt.boldadmin.nexus.api.gateway.ReverseGeocoder
import lt.boldadmin.nexus.api.type.valueobject.location.Coordinates

open class GoogleMapsReverseGeocoderAdapter(private val reverseGeocoder: GoogleMapsReverseGeocoder): ReverseGeocoder {

    override fun toAddress(coordinates: Coordinates): String =
        try {
            toAddressWithoutExceptionHandling(coordinates)
        } catch (e: ApiException) {
            throw ReverseGeocoderException("Api exception: ${e.message}")
        } catch (e: IllegalStateException) {
            throw ReverseGeocoderException("Illegal state exception: ${e.message}")
        }

    override fun toPlusCode(coordinates: Coordinates): String =
        OpenLocationCode(coordinates.latitude, coordinates.longitude).code

    private fun toAddressWithoutExceptionHandling(coordinates: Coordinates): String {
        val results = reverseGeocoder.geocode(coordinates)

        if (results.isEmpty())
            throw CoordinatesNotFoundException(
                "Location not found by coordinates: ${coordinates.latitude} ${coordinates.longitude}"
            )
        return results.first().formattedAddress
    }

}
