package lt.boldadmin.nexus.plugin.google.maps.geocode

import com.google.maps.errors.ApiException
import com.google.openlocationcode.OpenLocationCode
import lt.boldadmin.nexus.api.ReverseGeocodeConverter
import lt.boldadmin.nexus.api.exception.LocationNotFoundException
import lt.boldadmin.nexus.api.exception.ReverseGeocodeConverterException

open class GoogleMapsGeocodeConverterAdapter(private val reverseGeocoder: ReverseGeocoder): ReverseGeocodeConverter {

    override fun convertToAddress(latitude: Double, longitude: Double): String {
        try {
            return convert(latitude, longitude)
        } catch (e: ApiException) {
            throw ReverseGeocodeConverterException("Api exception: ${e.message}")
        } catch (e: IllegalStateException) {
            throw ReverseGeocodeConverterException("Illegal state exception: ${e.message}")
        }
    }

    override fun convertToPlusCode(latitude: Double, longitude: Double): String =
        OpenLocationCode(latitude, longitude).code

    private fun convert(latitude: Double, longitude: Double): String {
        val results = reverseGeocoder.geocode(latitude, longitude)

        if (results.isEmpty())
            throw LocationNotFoundException(
                "Location not found by coordinates: $latitude $longitude"
            )
        return results.first().formattedAddress
    }

}
