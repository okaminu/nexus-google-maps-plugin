package lt.boldadmin.nexus.plugin.google.maps.geocode

import com.google.maps.errors.ApiException
import com.google.maps.model.GeocodingResult
import lt.boldadmin.nexus.api.exception.GeocoderException
import lt.boldadmin.nexus.api.exception.LocationNotFoundException

abstract class GeocoderAdapter<T> {

    abstract fun getGeocodeResults(location: T): Array<GeocodingResult>

    abstract fun getLocationNotFoundException(location: T): LocationNotFoundException

    internal fun toGeocodingResult(location: T) =
        try {
            getGeocodeResults(location).apply {
                if (this.isEmpty())
                    throw getLocationNotFoundException(location)
            }.first()
        } catch (e: ApiException) {
            throw GeocoderException("Api exception: ${e.message}")
        } catch (e: IllegalStateException) {
            throw GeocoderException("Illegal state exception: ${e.message}")
        }

}
