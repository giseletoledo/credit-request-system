package me.dio.creditrequestsystem.repository

import me.dio.creditrequestsystem.entity.Credit
import org.springframework.data.jpa.repository.JpaRepository

interface CreditRepository: JpaRepository<Credit, Long> {
}