package lt.boldadmin.nexus.plugin.google.maps.geocode

import com.google.maps.model.GeocodingResult
import lt.boldadmin.nexus.api.exception.LocationNotFoundException

abstract class GeocoderAdapter<T> {

    abstract fun getGeocodeResults(location: T): Array<GeocodingResult>

    abstract fun getLocationNotFoundException(location: T): LocationNotFoundException

    internal fun toGeocodingResultWithoutExceptionHandling(location: T) =
        getGeocodeResults(location).apply {
            if (this.isEmpty())
                throw getLocationNotFoundException(location)
        }.first()

}
