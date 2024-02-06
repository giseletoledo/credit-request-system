package me.dio.creditrequestsystem.repository

import me.dio.creditrequestsystem.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository: JpaRepository<Customer, Long> {
}