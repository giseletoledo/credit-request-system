package me.dio.creditrequestsystem.controller

import me.dio.creditrequestsystem.dto.CustomerDto
import me.dio.creditrequestsystem.dto.CustomerView
import me.dio.creditrequestsystem.entity.Customer
import me.dio.creditrequestsystem.service.CustomerService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/customers")
class CustomerResource(
    private val customerService: CustomerService
) {
    @PostMapping
    fun saveCustomer(@RequestBody customerDto: CustomerDto) :String{
        val savedCustomer = this.customerService.save(customerDto.toEntity())
        return "Customer ${savedCustomer.email} saved!"
    }
    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) : CustomerView {
        val customer : Customer = this.customerService.findById(id)
        return CustomerView(customer)
    }

    @DeleteMapping("/{id}")
    fun deleteCustomer(@PathVariable id: Long) = this.customerService.delete(id)

    @PatchMapping
    fun updateCustomer(@RequestParam(value = "customerId") id: Long, @RequestBody customerUpdateDto: me.dio.creditrequestsystem.dto.CustomerUpdateDto) : CustomerView{
        val customer = this.customerService.findById(id)
        val customerToUpdate : Customer = customerUpdateDto.toEntity(customer)
        val customerUpdated : Customer = this.customerService.save(customerToUpdate)
        return CustomerView(customerUpdated)
    }
}