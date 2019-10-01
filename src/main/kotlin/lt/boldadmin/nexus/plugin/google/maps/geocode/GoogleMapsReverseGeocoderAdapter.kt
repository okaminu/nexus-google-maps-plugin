package lt.boldadmin.nexus.plugin.google.maps.geocode

import com.google.maps.errors.ApiException
import com.google.openlocationcode.OpenLocationCode
import lt.boldadmin.nexus.api.ReverseGeocoder
import lt.boldadmin.nexus.api.exception.LocationNotFoundException
import lt.boldadmin.nexus.api.exception.ReverseGeocodeConverterException
import lt.boldadmin.nexus.api.type.valueobject.Coordinates

open class GoogleMapsReverseGeocoderAdapter(private val reverseGeocoder: GoogleMapsReverseGeocoder): ReverseGeocoder {

    override fun toAddress(coordinates: Coordinates): String =
        try {
            toAddressWithoutExceptionHandling(coordinates)
        } catch (e: ApiException) {
            throw ReverseGeocodeConverterException("Api exception: ${e.message}")
        } catch (e: IllegalStateException) {
            throw ReverseGeocodeConverterException("Illegal state exception: ${e.message}")
        }

    override fun toPlusCode(coordinates: Coordinates): String =
        OpenLocationCode(coordinates.latitude, coordinates.longitude).code

    private fun toAddressWithoutExceptionHandling(coordinates: Coordinates) =
        reverseGeocoder.geocode(coordinates).apply {
            if (this.isEmpty())
                throw LocationNotFoundException(
                    "Location not found by coordinates: ${coordinates.latitude} ${coordinates.longitude}"
                )
        }.first().formattedAddress

}
