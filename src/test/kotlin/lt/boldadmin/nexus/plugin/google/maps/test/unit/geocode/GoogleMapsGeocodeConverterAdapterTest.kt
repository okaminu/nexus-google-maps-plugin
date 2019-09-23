package lt.boldadmin.nexus.plugin.google.maps.test.unit.geocode

import com.google.maps.errors.ApiException
import com.google.maps.model.GeocodingResult
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import lt.boldadmin.nexus.api.exception.LocationNotFoundException
import lt.boldadmin.nexus.api.exception.ReverseGeocodeConverterException
import lt.boldadmin.nexus.plugin.google.maps.geocode.GoogleMapsGeocodeConverterAdapter
import lt.boldadmin.nexus.plugin.google.maps.geocode.ReverseGeocoder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GoogleMapsGeocodeConverterAdapterTest {

    @MockK
    private lateinit var reverseGeocoderStub: ReverseGeocoder

    private lateinit var adapter: GoogleMapsGeocodeConverterAdapter

    @BeforeEach
    fun `Set up`() {
        adapter = GoogleMapsGeocodeConverterAdapter(reverseGeocoderStub)
    }

    @Test
    fun `Converts coordinates to address`() {
        val expectedAddress = "address"
        val latitude = 15.0
        val longitude = 16.0
        every { reverseGeocoderStub.geocode(latitude, longitude) } returns
            arrayOf(GeocodingResult().apply { formattedAddress = expectedAddress })

        val actualAddress = adapter.convertToAddress(latitude, longitude)

        assertEquals(expectedAddress, actualAddress)
    }

    @Test
    fun `Throws exception when location is not found`() {
        val latitude = 15.0
        val longitude = 16.0
        every { reverseGeocoderStub.geocode(latitude, longitude) } returns emptyArray()

        val exception = assertThrows(LocationNotFoundException::class.java) {
            adapter.convertToAddress(latitude, longitude)
        }

        assertTrue(exception.message!!.contains(latitude.toString()))
        assertTrue(exception.message!!.contains(longitude.toString()))
    }

    @Test
    fun `Throws exception on ApiException`() {
        val latitude = 15.0
        val longitude = 16.0
        val exceptionStub: ApiException = mockk()
        every { exceptionStub.message } returns "exception"
        every { reverseGeocoderStub.geocode(latitude, longitude) } throws exceptionStub

        val exception = assertThrows(ReverseGeocodeConverterException::class.java) {
            adapter.convertToAddress(latitude, longitude)
        }

        assertTrue(exception.message!!.contains(exceptionStub.message!!))
    }

    @Test
    fun `Throws exception on IllegalStateException`() {
        val latitude = 15.0
        val longitude = 16.0
        val exceptionStub: IllegalStateException = mockk()
        every { exceptionStub.message } returns "exception"
        every { reverseGeocoderStub.geocode(latitude, longitude) } throws exceptionStub

        val exception = assertThrows(ReverseGeocodeConverterException::class.java) {
            adapter.convertToAddress(latitude, longitude)
        }

        assertTrue(exception.message!!.contains(exceptionStub.message!!))
    }

    @Test
    fun `Converts coordinates to plus code`() {
        val expectedPlusCode = "8Q7XJVGM+M5"

        val actualPlusCode = adapter.convertToPlusCode(35.6267108, 139.8828839)

        assertEquals(expectedPlusCode, actualPlusCode)
    }

}
