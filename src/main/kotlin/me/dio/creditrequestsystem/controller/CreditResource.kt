package me.dio.creditrequestsystem.controller

import me.dio.creditrequestsystem.dto.CreditDto
import me.dio.creditrequestsystem.dto.CreditView
import me.dio.creditrequestsystem.dto.CreditViewList
import me.dio.creditrequestsystem.entity.Credit
import me.dio.creditrequestsystem.service.CreditService
import org.springframework.web.bind.annotation.*
import java.util.UUID
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/credits")
class CreditResource(
    private val creditService: CreditService
) {
    @PostMapping
    fun saveCredit(@RequestBody creditDto: CreditDto) : String{
        val credit : Credit = this.creditService.save(creditDto.toEntity())
        return "Credit ${credit.creditCode} - Customer ${credit.customer?.firstName} saved!"
    }

    @GetMapping
    fun findAllByCustomerId(@RequestParam(value = "customerId") custumerId: Long) : List<CreditViewList>{
        //usa o stream para converter o tipo para obter retorno esperado que é um List<CreditViewList> e não Credit
       return this.creditService.findAllByCustomer(custumerId).stream().map { credit: Credit -> CreditViewList(credit)}.collect(
            Collectors.toList())
    }

    @GetMapping("/{creditCode}")
    fun findByCreditCode(@RequestParam(value = "customerId") custumerId: Long, @PathVariable creditCode: UUID): CreditView {
        val credit: Credit = this.creditService.findByCreditCode(custumerId, creditCode)
        return CreditView(credit)
    }
}