package com.example.rest.restsql.Controller;

import com.example.rest.restsql.User.Users;
import com.example.rest.restsql.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @GetMapping
  public ResponseEntity<?> listar(Pageable pageable) {
    return new ResponseEntity<>(
      userRepository.findAll(pageable),
      HttpStatus.OK
    );
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> adicionar(@RequestBody Users user) {
    //validar CPF
    user.ValicarCpf(user.getCpf());
    if (user.getValicarCpf() == false) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    if (user.getNome().length() < 50) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //checar se tem 2 CFP igual
    Long i = userRepository.count();
    for (i--; i >= 0;) {
      if (user.getCpf() == userRepository.findById(i).get().getCpf()) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    }
    //Salvar no Banco de Dados
    return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
  }

  @PutMapping("/user/{id}")
  public ResponseEntity<?> atualizar(
    @PathVariable Number id,
    @RequestBody Users user
  ) {
    Long idLong = id.longValue();
    Users userAtual = userRepository.findById(idLong).get();
    BeanUtils.copyProperties(user, userAtual, "id");
    //validar CPF
    user.ValicarCpf(user.getCpf());
    if (user.getValicarCpf() == false) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    //validar nome
    if (user.getNome().length() > 50) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    //validar se tem 2 cpf igual
    Long i = userRepository.count();
    for (i--; i >= 0;) {
      if (user.getCpf() == userRepository.findById(i).get().getCpf()) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    }

    //Salvar no Banco de Dados
    return new ResponseEntity<>(userRepository.save(userAtual), HttpStatus.OK);
  }

  @DeleteMapping("/user/{id}")
  public void delete(@PathVariable Long id) {
    // era no caso do id ser number
    // Long idLong = id.longValue();

    //Deletar no Banco de dados
    userRepository.deleteById(id);
  }
}
