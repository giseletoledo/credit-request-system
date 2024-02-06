package me.dio.creditrequestsystem.service

import me.dio.creditrequestsystem.entity.Credit
import me.dio.creditrequestsystem.entity.Customer
import org.hibernate.validator.constraints.UUID

interface ICustomerService {
    fun save(customer: Customer): Customer
    fun findById(id: Long) : Customer
    fun delete(id: Long): Customer
}