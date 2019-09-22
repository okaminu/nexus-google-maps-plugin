package lt.boldadmin.nexus.google.maps.plugin.time.zone

import com.google.maps.TimeZoneApi
import com.google.maps.model.LatLng
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import lt.boldadmin.nexus.google.maps.plugin.createApiContext
import java.util.*

object TimeZoneConverter {

    internal fun convert(coordinates: Coordinates): TimeZone = TimeZoneApi.getTimeZone(
        createApiContext(),
        LatLng(coordinates.latitude, coordinates.longitude)
    ).await()

}
