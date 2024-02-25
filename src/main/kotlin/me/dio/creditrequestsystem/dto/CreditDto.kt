package me.dio.creditrequestsystem.dto

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import me.dio.creditrequestsystem.entity.Credit
import me.dio.creditrequestsystem.entity.Customer
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDto(
    @NotNull(message = "Empty value")
    val creditValue: BigDecimal,
    @field:Future
    val dayFirstOfInstallment: LocalDate,
    @field:Min(value = 1) @field:Max(value = 48)
    val numberOfInstallments: Int,
    @field:NotNull(message = "Invalid id")
    val customerId: Long
) {
    fun toEntity(): Credit = Credit(
        creditValue = this.creditValue,
        dayFirstInstallment = this.dayFirstOfInstallment,
        numberOfInstallments = this.numberOfInstallments,
        customer = Customer(id = this.customerId)
    )
}
