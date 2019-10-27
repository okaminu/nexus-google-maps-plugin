package lt.boldadmin.nexus.plugin.google.maps.timezone

import com.google.maps.errors.ApiException
import lt.boldadmin.nexus.api.gateway.TimeZoneConverter
import lt.boldadmin.nexus.api.exception.TimeZoneConverterException
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import java.time.ZoneId

open class GoogleMapsTimeZoneAdapter(private val converter: GoogleMapsTimeZoneConverter):
    TimeZoneConverter {

    override fun convert(coordinates: Coordinates): ZoneId =
        try {
            converter.convert(coordinates).toZoneId()
        } catch (e: ApiException) {
            throw TimeZoneConverterException("Api exception: ${e.message} at coordinates: $coordinates")
        }

}
