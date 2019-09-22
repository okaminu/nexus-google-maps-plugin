package lt.boldadmin.nexus.google.maps.plugin.time.zone

import com.google.maps.errors.ApiException
import lt.boldadmin.nexus.api.TimeZoneConverter
import lt.boldadmin.nexus.api.exception.TimeZoneConverterGatewayException
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import lt.boldadmin.nexus.google.maps.plugin.time.zone.TimeZoneConverter as TimeZoneConverterImpl
import java.time.ZoneId

open class GoogleMapsTimeZoneConverterAdapter(private val timeZoneConverter: TimeZoneConverterImpl): TimeZoneConverter {

    override fun convert(coordinates: Coordinates): ZoneId =
        try {
            println("GAUNA timezone")
            timeZoneConverter.convert(coordinates).toZoneId()
        } catch (e: ApiException) {
            throw TimeZoneConverterGatewayException("Api exception: ${e.message}")
        }

}
