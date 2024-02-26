package me.dio.creditrequestsystem.repository

import me.dio.creditrequestsystem.entity.Address
import me.dio.creditrequestsystem.entity.Customer
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest {
    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Autowired
    lateinit var testEntityManager: TestEntityManager

    private lateinit var customer1: Customer
    private lateinit var customer2: Customer

    @BeforeEach
    fun setup() {
        customer1 = testEntityManager.persist(buildCustomer(firstName = "John"))
        customer2 = testEntityManager.persist(buildCustomer(firstName = "Jane"))
    }

    @Test
    fun `should find customer by ID`() {
        //given
        val customerId = customer1.id
        //when
        val foundCustomer = customerId?.let { customerRepository.findById(it).orElse(null) }
        //then
        Assertions.assertThat(foundCustomer).isNotNull
        Assertions.assertThat(foundCustomer?.firstName).isEqualTo(customer1.firstName)
    }

    private fun buildCustomer(
        firstName: String,
        lastName: String = "Doe",
        cpf: String = "12345678900",
        email: String = "john.doe@example.com",
        income: BigDecimal = BigDecimal.valueOf(50000.0),
        zipCode: String = "12345",
        street: String = "Example Street",
    ): Customer {
        val customer = Customer(
            firstName = firstName,
            lastName = lastName,
            cpf = cpf,
            email = email,
            income = income
        )
        customer.address = Address(
            zipCode = zipCode,
            street = street
        )
        return customer
    }
}
