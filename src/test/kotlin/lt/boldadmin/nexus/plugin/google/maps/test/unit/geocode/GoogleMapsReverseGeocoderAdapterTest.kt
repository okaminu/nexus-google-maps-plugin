package lt.boldadmin.nexus.plugin.google.maps.test.unit.geocode

import com.google.maps.errors.ApiException
import com.google.maps.model.GeocodingResult
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import lt.boldadmin.nexus.api.exception.LocationNotFoundException
import lt.boldadmin.nexus.api.exception.ReverseGeocodeConverterException
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import lt.boldadmin.nexus.plugin.google.maps.geocode.GoogleMapsReverseGeocoderAdapter
import lt.boldadmin.nexus.plugin.google.maps.geocode.GoogleMapsReverseGeocoder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GoogleMapsReverseGeocoderAdapterTest {

    @MockK
    private lateinit var reverseGeocoderStub: GoogleMapsReverseGeocoder

    private lateinit var adapter: GoogleMapsReverseGeocoderAdapter

    @BeforeEach
    fun `Set up`() {
        adapter = GoogleMapsReverseGeocoderAdapter(reverseGeocoderStub)
    }

    @Test
    fun `Converts coordinates to address`() {
        val expectedAddress = "address"
        every { reverseGeocoderStub.geocode(COORDINATES) } returns
            arrayOf(GeocodingResult().apply { formattedAddress = expectedAddress })

        val actualAddress = adapter.toAddress(COORDINATES)

        assertEquals(expectedAddress, actualAddress)
    }

    @Test
    fun `Throws exception when location is not found`() {
        every { reverseGeocoderStub.geocode(COORDINATES) } returns emptyArray()

        val exception = assertThrows(LocationNotFoundException::class.java) {
            adapter.toAddress(COORDINATES)
        }

        assertTrue(exception.message!!.contains(COORDINATES.latitude.toString()))
        assertTrue(exception.message!!.contains(COORDINATES.longitude.toString()))
    }

    @Test
    fun `Throws exception on ApiException`() {
        val exceptionStub: ApiException = mockk()
        every { exceptionStub.message } returns "exception"
        every { reverseGeocoderStub.geocode(COORDINATES) } throws exceptionStub

        val exception = assertThrows(ReverseGeocodeConverterException::class.java) {
            adapter.toAddress(COORDINATES)
        }

        assertTrue(exception.message!!.contains(exceptionStub.message!!))
    }

    @Test
    fun `Throws exception on IllegalStateException`() {
        val exceptionStub: IllegalStateException = mockk()
        every { exceptionStub.message } returns "exception"
        every { reverseGeocoderStub.geocode(COORDINATES) } throws exceptionStub

        val exception = assertThrows(ReverseGeocodeConverterException::class.java) {
            adapter.toAddress(COORDINATES)
        }

        assertTrue(exception.message!!.contains(exceptionStub.message!!))
    }

    @Test
    fun `Converts coordinates to plus code`() {
        val expectedPlusCode = "8Q7XJVGM+M5"

        val actualPlusCode = adapter.toPlusCode(Coordinates(35.6267108, 139.8828839))

        assertEquals(expectedPlusCode, actualPlusCode)
    }

    companion object {
        private val COORDINATES = Coordinates(15.0, 16.0)
    }

}
