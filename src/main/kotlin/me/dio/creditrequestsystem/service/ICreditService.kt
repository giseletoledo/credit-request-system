package me.dio.creditrequestsystem.service

import me.dio.creditrequestsystem.entity.Credit
import org.hibernate.validator.constraints.UUID

interface ICreditService {

    fun save(credit: Credit):Credit
    fun findAllByCustomer(customerId: Long) : List<Credit>
    fun findByCreditCode(creditCode: UUID): Credit
}