package com.example.enderecos.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.enderecos.models.Endereco;
import com.example.enderecos.models.Usuario;
import com.example.enderecos.repository.EnderecoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.enderecos.repository.UsuarioRepository;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Endereco saveOrUpdate(Endereco endereco){
        return enderecoRepository.save(endereco);
    }

    public ResponseEntity<?> cadastrar_endereco(Long id,Endereco endereco){
        Optional<Usuario> user = usuarioRepository.findById(id);

        if(user.isEmpty()){
            Map<String,String> erro = new HashMap<String,String>();
            erro.put("Erro", "Usuario não encontrado");
            return new ResponseEntity<>(erro,HttpStatus.CREATED);
        }

        
        endereco.setUsuario(user.get());
        enderecoRepository.save(endereco);
        user.get().getEndereco().add(endereco);

        usuarioRepository.save(user.get());
        return new ResponseEntity<>(endereco,HttpStatus.CREATED);
        
    }
    
}
