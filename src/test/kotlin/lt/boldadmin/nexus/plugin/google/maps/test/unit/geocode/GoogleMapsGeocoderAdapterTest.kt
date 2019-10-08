package lt.boldadmin.nexus.plugin.google.maps.test.unit.geocode

import com.google.maps.errors.ApiException
import com.google.maps.model.GeocodingResult
import com.google.maps.model.Geometry
import com.google.maps.model.LatLng
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import lt.boldadmin.nexus.api.exception.LocationNotFoundException
import lt.boldadmin.nexus.api.exception.GeocoderException
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import lt.boldadmin.nexus.plugin.google.maps.geocode.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GoogleMapsGeocoderAdapterTest {

    @MockK
    private lateinit var geocoderStub: GoogleMapsGeocoder

    private lateinit var adapter: GoogleMapsGeocoderAdapter

    @BeforeEach
    fun `Set up`() {
        adapter = GoogleMapsGeocoderAdapter(geocoderStub)
    }

    @Test
    fun `Converts address to coordinates`() {
        val expectedCoordinates = Coordinates(10.0, 11.0)
        every { geocoderStub.geocode(ADDRESS) } returns
            arrayOf(GeocodingResult().apply {
                geometry = Geometry().apply {
                    location = LatLng(expectedCoordinates.latitude, expectedCoordinates.longitude)
                }
            })

        val actualCoordinates = adapter.toCoordinates(ADDRESS)

        assertEquals(expectedCoordinates, actualCoordinates)
    }

    @Test
    fun `Throws exception when location is not found`() {
        every { geocoderStub.geocode(ADDRESS) } returns emptyArray()

        val exception = assertThrows(LocationNotFoundException::class.java) {
            adapter.toCoordinates(ADDRESS)
        }

        assertTrue(exception.message!!.contains(ADDRESS))
    }

    @Test
    fun `Throws exception on ApiException`() {
        val exceptionStub: ApiException = mockk()
        every { exceptionStub.message } returns "exception"
        every { geocoderStub.geocode(ADDRESS) } throws exceptionStub

        val exception = assertThrows(GeocoderException::class.java) {
            adapter.toCoordinates(ADDRESS)
        }

        assertTrue(exception.message!!.contains(exceptionStub.message!!))
    }

    @Test
    fun `Throws exception on IllegalStateException`() {
        val exceptionStub: IllegalStateException = mockk()
        every { exceptionStub.message } returns "exception"
        every { geocoderStub.geocode(ADDRESS) } throws exceptionStub

        val exception = assertThrows(GeocoderException::class.java) {
            adapter.toCoordinates(ADDRESS)
        }

        assertTrue(exception.message!!.contains(exceptionStub.message!!))
    }

    companion object {
        private val ADDRESS = "address"
    }

}
