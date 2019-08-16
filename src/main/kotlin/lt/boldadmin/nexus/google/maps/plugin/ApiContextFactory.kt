package lt.boldadmin.nexus.google.maps.plugin

import com.google.maps.GeoApiContext

internal fun createApiContext(): GeoApiContext = GeoApiContext.Builder()
    .apiKey(System.getenv("GOOGLE_MAPS_API_KEY"))
    .build()
