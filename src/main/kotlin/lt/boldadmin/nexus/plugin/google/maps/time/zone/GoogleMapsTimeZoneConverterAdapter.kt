package lt.boldadmin.nexus.plugin.google.maps.time.zone

import com.google.maps.errors.ApiException
import lt.boldadmin.nexus.api.TimeZoneConverter
import lt.boldadmin.nexus.api.exception.TimeZoneConverterGatewayException
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import lt.boldadmin.nexus.plugin.google.maps.time.zone.TimeZoneConverter as TimeZoneConverterImpl
import java.time.ZoneId

open class GoogleMapsTimeZoneConverterAdapter(private val timeZoneConverter: TimeZoneConverterImpl): TimeZoneConverter {

    override fun convert(coordinates: Coordinates): ZoneId =
        try {
            timeZoneConverter.convert(coordinates).toZoneId()
        } catch (e: ApiException) {
            throw TimeZoneConverterGatewayException("Api exception: ${e.message}")
        }

}
