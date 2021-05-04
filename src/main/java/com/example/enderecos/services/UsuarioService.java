package com.example.enderecos.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.enderecos.models.Endereco;
import com.example.enderecos.models.Usuario;
import com.example.enderecos.repository.EnderecoRepository;
import com.example.enderecos.repository.UsuarioRepository;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public ResponseEntity<?> saveOrUpdate(Usuario usuario){
        Optional<Usuario> user_cpf = usuarioRepository.findByCpfAllIgnoreCase(usuario.getCpf());
        Optional<Usuario> user_email = usuarioRepository.findByEmailAllIgnoreCase(usuario.getEmail());
 
        if(user_cpf.isPresent()){
            Map<String,String> erro = new HashMap<String,String>();
            erro.put("Erro", "CPF já existe");
            return new ResponseEntity<>(erro,HttpStatus.BAD_REQUEST);
        }
        if(user_email.isPresent()){
            Map<String,String> erro = new HashMap<String,String>();
            erro.put("Erro", "Email já existe");
            return new ResponseEntity<>(erro,HttpStatus.BAD_REQUEST);
        }
        

        
        return new ResponseEntity<Usuario>(usuarioRepository.save(usuario),HttpStatus.CREATED);
 
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
        user.get().getEnderecos().add(endereco);

        usuarioRepository.save(user.get());
        return new ResponseEntity<>(endereco,HttpStatus.CREATED);
        
    }

    public ResponseEntity<?> findById(Long id){
        if (usuarioRepository.findById(id).isEmpty()){
            Map<String,String> erro = new HashMap<String,String>();
            erro.put("Erro", "Usuario não encontrado");
            return new ResponseEntity<>(erro,HttpStatus.NOT_FOUND);
        }
        Optional<Usuario> user = usuarioRepository.findById(id);

        return new ResponseEntity<>(user.get(),HttpStatus.OK);
    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }
    
}
