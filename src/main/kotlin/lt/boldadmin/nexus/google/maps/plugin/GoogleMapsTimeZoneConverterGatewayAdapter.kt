package lt.boldadmin.nexus.google.maps.plugin

import com.google.maps.errors.ApiException
import lt.boldadmin.nexus.api.TimeZoneConverterGateway
import lt.boldadmin.nexus.api.exception.TimeZoneConverterGatewayException
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import java.time.ZoneId

open class GoogleMapsTimeZoneConverterGatewayAdapter(
    private val timeZoneConverter: TimeZoneConverter
): TimeZoneConverterGateway {

    override fun convert(coordinates: Coordinates): ZoneId =
        try {
            timeZoneConverter.convert(coordinates).toZoneId()
        } catch (e: ApiException) {
            throw TimeZoneConverterGatewayException("Api exception: ${e.message}")
        }

}
