package lt.boldadmin.nexus.google.maps.plugin.test.unit

import com.google.maps.errors.ApiException
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import lt.boldadmin.nexus.api.exception.TimeZoneConverterGatewayException
import lt.boldadmin.nexus.api.type.valueobject.Coordinates
import lt.boldadmin.nexus.google.maps.plugin.GoogleMapsTimeZoneConverterGatewayAdapter
import lt.boldadmin.nexus.google.maps.plugin.TimeZoneConverter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.ZoneId
import java.util.*

@ExtendWith(MockKExtension::class)
class GoogleMapsTimeZoneConverterGatewayAdapterTest {

    @MockK
    private lateinit var converterStub: TimeZoneConverter

    private lateinit var adapter: GoogleMapsTimeZoneConverterGatewayAdapter

    @BeforeEach
    fun `Set up`() {
        adapter = GoogleMapsTimeZoneConverterGatewayAdapter(converterStub)
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

         val exception = assertThrows(TimeZoneConverterGatewayException::class.java) {
             adapter.convert(COORDINATES)
         }

         assertTrue(exception.message!!.contains("error"))
     }

    companion object {
        val COORDINATES = Coordinates(15.0, 16.0)
    }

}


