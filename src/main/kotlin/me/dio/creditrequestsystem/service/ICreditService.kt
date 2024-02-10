package me.dio.creditrequestsystem.service

import me.dio.creditrequestsystem.entity.Credit
import java.util.*

interface ICreditService {

    fun save(credit: Credit):Credit
    fun findAllByCustomer(customerId: Long) : List<Credit>
    fun findByCreditCode(customerId: Long,creditCode: UUID): Credit
}