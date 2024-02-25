package me.dio.creditrequestsystem.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import me.dio.creditrequestsystem.entity.Address
import me.dio.creditrequestsystem.entity.Customer
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal

data class CustomerDto(
    @field:NotEmpty(message = "Empty field")
    val firstName: String,
    @field:NotEmpty(message = "Empty field")
    val lastName: String,
    @field:NotEmpty(message = "Empty field")
    @CPF(message = "Invalid cpf")
    val cpf: String,
    @NotNull(message = "Empty income")
    val income: BigDecimal,
    @field:NotEmpty(message = "Empty email")
    val email: String,
    @field:NotEmpty(message = "Empty password")
    val password: String,
    @field:NotEmpty(message = "Empty zipCode")
    val zipCode: String,
    @field:NotEmpty(message = "Empty field")
    val street: String
    ) {
        fun toEntity(): Customer = Customer(
            firstName = this.firstName,
            lastName = this.lastName,
            cpf = this.cpf,
            income = this.income,
            email = this.email,
            password = this.password,
            address = Address(
                zipCode = this.zipCode,
                street = this.street
            )
        )
    }

