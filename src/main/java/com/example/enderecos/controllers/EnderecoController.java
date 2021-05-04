package com.example.enderecos.controllers;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.enderecos.models.Endereco;
import com.example.enderecos.services.EnderecoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(value = "/")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping("/endereco/{id}")
    public ResponseEntity<?> postUsuarioEndereco(@PathVariable(value = "id") Long id,@RequestBody Endereco endereco){
        
        return enderecoService.cadastrar_endereco(id, endereco);
        
    }
    
}
