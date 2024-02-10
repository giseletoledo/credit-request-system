package me.dio.creditrequestsystem.service

import me.dio.creditrequestsystem.entity.Credit
import me.dio.creditrequestsystem.repository.CreditRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreditService(
    private val creditRepository: CreditRepository,
    private val customerService: CustomerService
) : ICreditService {
    override fun save(credit: Credit): Credit {
        credit.apply {
            customer = customerService.findById(credit.customer?.id!!)
        }
        return this.creditRepository.save(credit)
    }

    override fun findAllByCustomer(customerId: Long): List<Credit> =
        this.creditRepository.findAllByCustomerId(customerId)
    override fun findByCreditCode(customerId: Long, creditCode: UUID): Credit {
        val credit = this.creditRepository.findByCreditCode(creditCode) ?: throw RuntimeException("Credit code not found")
        return if(credit.customer?.id == customerId) credit else throw RuntimeException("Contact admin")
    }
}