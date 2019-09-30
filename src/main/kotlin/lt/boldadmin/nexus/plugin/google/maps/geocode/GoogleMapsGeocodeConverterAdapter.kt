package lt.boldadmin.nexus.plugin.google.maps.geocode

import com.google.maps.errors.ApiException
import com.google.openlocationcode.OpenLocationCode
import lt.boldadmin.nexus.api.ReverseGeocodeConverter
import lt.boldadmin.nexus.api.exception.LocationNotFoundException
import lt.boldadmin.nexus.api.exception.ReverseGeocodeConverterException
import lt.boldadmin.nexus.api.type.valueobject.Coordinates

open class GoogleMapsGeocodeConverterAdapter(private val reverseGeocoder: ReverseGeocoder): ReverseGeocodeConverter {

    override fun convertToAddress(coordinates: Coordinates) =
        try {
            convert(coordinates)
        } catch (e: ApiException) {
            throw ReverseGeocodeConverterException("Api exception: ${e.message}")
        } catch (e: IllegalStateException) {
            throw ReverseGeocodeConverterException("Illegal state exception: ${e.message}")
        }

    override fun convertToPlusCode(coordinates: Coordinates): String =
        OpenLocationCode(coordinates.latitude, coordinates.longitude).code

    private fun convert(coordinates: Coordinates): String {
        val results = reverseGeocoder.geocode(coordinates)

        if (results.isEmpty())
            throw LocationNotFoundException(
                "Location not found by coordinates: ${coordinates.latitude} ${coordinates.longitude}"
            )
        return results.first().formattedAddress
    }

}
