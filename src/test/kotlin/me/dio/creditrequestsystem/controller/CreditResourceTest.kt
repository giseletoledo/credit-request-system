import com.fasterxml.jackson.databind.ObjectMapper
import me.dio.creditrequestsystem.controller.CreditResource
import me.dio.creditrequestsystem.dto.CreditDto
import me.dio.creditrequestsystem.entity.Credit
import me.dio.creditrequestsystem.repository.CreditRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
internal class CreditResourceTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Mock
    private lateinit var creditRepository: CreditRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @InjectMocks
    private lateinit var creditResource: CreditResource

    companion object {
        private const val URL = "/api/credits"
    }

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(creditResource).build()
    }

    @AfterEach
    fun tearDown() {
        Mockito.reset(creditRepository)
    }

    @Test
    fun `should save a credit and return 201 status`() {
        // Given
        val creditDto = CreditDto(BigDecimal.TEN, LocalDate.now(), 12, 1L)
        val credit = Credit(creditValue = BigDecimal.TEN, dayFirstInstallment = LocalDate.now(), numberOfInstallments = 12)

        Mockito.`when`(creditRepository.save(any())).thenReturn(credit)

        // When - Then
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creditDto))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().string("Credit ${credit.creditCode} - Customer null saved!"))
    }


    @Test
    fun `should return 404 status when credit is not found by creditCode`() {
        // Given
        val customerId = 1L
        val creditCode = UUID.randomUUID()

        Mockito.`when`(creditRepository.findByCreditCode(creditCode)).thenReturn(null)

        // When - Then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/$creditCode?customerId=$customerId")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `should return the credit when found by creditCode`() {
        // Given
        val customerId = 1L
        val creditCode = UUID.randomUUID()
        val credit = Credit(creditCode = creditCode, creditValue = BigDecimal.TEN, dayFirstInstallment = LocalDate.now(), numberOfInstallments = 12)

        Mockito.`when`(creditRepository.findByCreditCode(creditCode)).thenReturn(credit)

        // When - Then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/$creditCode?customerId=$customerId")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.creditCode").value(creditCode.toString()))
    }

}
