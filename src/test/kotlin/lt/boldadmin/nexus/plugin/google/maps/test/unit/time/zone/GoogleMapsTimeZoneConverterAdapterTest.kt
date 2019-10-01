package lt.boldadmin.nexus.plugin.google.maps.test.unit.time.zone

import com.google.maps.errors.ApiException
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import lt.boldadmin.nexus.api.exception.TimeZoneConverterException
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import lt.boldadmin.nexus.plugin.google.maps.time.zone.GoogleMapsTimeZoneConverter
import lt.boldadmin.nexus.plugin.google.maps.time.zone.GoogleMapsTimeZoneConverterAdapter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.ZoneId
import java.util.*

@ExtendWith(MockKExtension::class)
class GoogleMapsTimeZoneConverterAdapterTest {

    @MockK
    private lateinit var converterStub: GoogleMapsTimeZoneConverter

    private lateinit var adapter: GoogleMapsTimeZoneConverterAdapter

    @BeforeEach
    fun `Set up`() {
        adapter = GoogleMapsTimeZoneConverterAdapter(converterStub)
    }

    @Test
    fun `Converts coordinates to time zone`() {
        val expectedTimeZone = ZoneId.of("Europe/Vilnius")
        every { converterStub.convert(COORDINATES) } returns TimeZone.getTimeZone(expectedTimeZone)

        val actualTimeZone = adapter.convert(COORDINATES)

        assertEquals(expectedTimeZone, actualTimeZone)
    }

     @Test
     fun `Throws exception on ApiException`() {
         every { converterStub.convert(COORDINATES) } throws ApiException.from("NOT_FOUND", "error")

         val exception = assertThrows(TimeZoneConverterException::class.java) {
             adapter.convert(COORDINATES)
         }

         assertTrue(exception.message!!.contains("error"))
     }

    companion object {
        val COORDINATES = Coordinates(15.0, 16.0)
    }

}
