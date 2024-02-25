package me.dio.creditrequestsystem.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import me.dio.creditrequestsystem.entity.Customer
import java.math.BigDecimal

data class CustomerUpdateDto(
    @field:NotEmpty(message = "Empty field")
    val firstName: String,
    @field:NotEmpty(message = "Empty field")
    val lastName: String,
    @NotNull(message = "Empty income")
    val income: BigDecimal,
    @field:NotEmpty(message = "Empty field")
    val zipCode: String,
    @field:NotEmpty(message = "Empty field")
    val street: String
) {
    fun toEntity(customer: Customer): Customer {
        customer.firstName = this.firstName
        customer.lastName = this.lastName
        customer.income = this.income
        customer.address.street = this.street
        customer.address.zipCode = this.zipCode

        return customer
    }
}
