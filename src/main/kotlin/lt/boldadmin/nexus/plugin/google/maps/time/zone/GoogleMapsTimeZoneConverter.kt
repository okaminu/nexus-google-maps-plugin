package lt.boldadmin.nexus.plugin.google.maps.time.zone

import com.google.maps.TimeZoneApi
import com.google.maps.model.LatLng
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import lt.boldadmin.nexus.plugin.google.maps.createApiContext
import java.util.*

object GoogleMapsTimeZoneConverter {

    internal fun convert(coordinates: Coordinates): TimeZone =
        TimeZoneApi.getTimeZone(
            createApiContext(),
            LatLng(coordinates.latitude, coordinates.longitude)
        ).await()

}
