package com.example.enderecos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.enderecos.error.ResourceNotFoundException;
import com.example.enderecos.models.Endereco;
import com.example.enderecos.models.Usuario;
import javax.validation.Valid;
import com.example.enderecos.repository.UsuarioRepository;
import com.example.enderecos.services.EnderecoService;
import com.example.enderecos.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    

    @PostMapping("")
    public ResponseEntity<?> postUsuario(@Valid @RequestBody Usuario usuario, Errors errors){
        return usuarioService.saveOrUpdate(usuario);
   
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(value = "id") Long id){
        return usuarioService.findById(id);
    }

    

    
    @GetMapping("/users")
    public List<Usuario> get(){
        return usuarioService.findAll();
    }

    @GetMapping("/user_cpf")
    public ResponseEntity<Usuario> get_user_by_cpf(){
        Optional<Usuario> user = usuarioRepository.findByCpfAllIgnoreCase("222222");
        if(user.isPresent()){
            return new ResponseEntity<Usuario>(user.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user_email")
    public ResponseEntity<Usuario> get_user_by_email(){
        Optional<Usuario> user = usuarioRepository.findByEmailAllIgnoreCase("222222");
        if(user.isPresent()){
            return new ResponseEntity<Usuario>(user.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
