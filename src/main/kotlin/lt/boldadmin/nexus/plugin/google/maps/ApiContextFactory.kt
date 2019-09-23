package lt.boldadmin.nexus.plugin.google.maps

import com.google.maps.GeoApiContext

internal fun createApiContext(): GeoApiContext =
    GeoApiContext.Builder()
        .apiKey(System.getenv("GOOGLE_MAPS_API_KEY"))
        .build()
