package lt.boldadmin.nexus.google.maps.plugin

import com.google.maps.TimeZoneApi
import com.google.maps.model.LatLng
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import java.util.*

object TimeZoneConverter {

    internal fun convert(coordinates: Coordinates): TimeZone = TimeZoneApi.getTimeZone(
        createApiContext(),
        LatLng(coordinates.latitude, coordinates.longitude)
    ).await()

}
