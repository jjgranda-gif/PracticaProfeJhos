package suda.com.practicajhos.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping

@Service

class ProductService {

    @Autowired


    open fun saludoService(): String {
        return "Hola mundo"
    }

}

import  default class ProductService