package me.dio.creditrequestsystem.service

import me.dio.creditrequestsystem.entity.Address
import me.dio.creditrequestsystem.entity.Credit
import me.dio.creditrequestsystem.entity.Customer
import me.dio.creditrequestsystem.repository.CreditRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Month
import java.util.*

internal class CreditServiceTest {

    @Mock
    private lateinit var creditRepository: CreditRepository

    @Mock
    private lateinit var customerService: CustomerService

    @InjectMocks
    private lateinit var creditService: CreditService

    private lateinit var customer: Customer
    private lateinit var credit: Credit

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        customer = buildCustomer()
        credit = buildCredit(customer = customer)
    }

    @Test
    fun `test save`() {
        `when`(customerService.findById(customer.id!!)).thenReturn(customer)
        `when`(creditRepository.save(credit)).thenReturn(credit)

        val savedCredit = creditService.save(credit)

        assertEquals(credit, savedCredit)
    }

    @Test
    fun `test findAllByCustomer`() {
        val credits = listOf(credit, credit.copy(id = 2)) // Assuming there are two credits associated with the customer

        `when`(creditRepository.findAllByCustomerId(customer.id!!)).thenReturn(credits)

        val foundCredits = creditService.findAllByCustomer(customer.id!!)

        assertEquals(credits, foundCredits)
    }

    @Test
    fun `test findByCreditCode`() {
        val creditCode = UUID.randomUUID()

        `when`(creditRepository.findByCreditCode(creditCode)).thenReturn(credit)

        val foundCredit = creditService.findByCreditCode(customer.id!!, creditCode)

        assertEquals(credit, foundCredit)
    }

    private fun buildCredit(
        creditValue: BigDecimal = BigDecimal.valueOf(500.0),
        dayFirstInstallment: LocalDate = LocalDate.of(2023, Month.APRIL, 22),
        numberOfInstallments: Int = 5,
        customer: Customer
    ): Credit = Credit(
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        customer = customer
    )

    private fun buildCustomer(
        firstName: String = "Cami",
        lastName: String = "Cavalcante",
        cpf: String = "28475934625",
        email: String = "camila@gmail.com",
        password: String = "12345",
        zipCode: String = "12345",
        street: String = "Rua da Cami",
        income: BigDecimal = BigDecimal.valueOf(1000.0),
    ) = Customer(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        password = password,
        address = Address(
            zipCode = zipCode,
            street = street,
        ),
        income = income,
    )
}
