package com.example.rest.restsql.Controller;

import com.example.rest.restsql.User.UserDocumentation;
import com.example.rest.restsql.repository.UserDocRepository;
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
@RequestMapping("/userDocumentation")
public class UserDocController {
  @Autowired
  private UserDocRepository userDocRepository;

  @GetMapping
  public ResponseEntity<?> listar(Pageable pageable) {
    return new ResponseEntity<>(
      userDocRepository.findAll(pageable),
      HttpStatus.OK
    );
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> adicionar(
    @RequestBody UserDocumentation userDocumentation
  ) {
    if (userDocumentation.getDocUser().length() < 50) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    //chekar os bytes

    if (userDocumentation.getDocument().longValue() > 2 * (10 * 6)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    //checar se tem 2 CFP igual
    Long i = userDocRepository.count();
    for (i--; i >= 0;) {
      if (
        userDocumentation.getDocType() ==
        userDocRepository.findById(i).get().getDocType()
      ) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    }
    //Salvar no Banco de Dados
    return new ResponseEntity<>(
      userDocRepository.save(userDocumentation),
      HttpStatus.OK
    );
  }

  @PutMapping("/userDocumentation/{id}")
  public ResponseEntity<?> atualizar(
    @PathVariable Number id,
    @RequestBody UserDocumentation user
  ) {
    Long idLong = id.longValue();
    UserDocumentation userAtual = userDocRepository.findById(idLong).get();
    BeanUtils.copyProperties(user, userAtual, "id");

    //validar nome
    if (user.getDocUser().length() < 50) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    //validar se tem 2 cpf igual
    Long i = userDocRepository.count();
    for (i--; i >= 0;) {
      if (
        user.getDocType() == userDocRepository.findById(i).get().getDocType()
      ) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    }

    //Salvar no Banco de Dados
    return new ResponseEntity<>(
      userDocRepository.save(userAtual),
      HttpStatus.OK
    );
  }

  @DeleteMapping("/userDocumentation/{id}")
  public void delete(@PathVariable Long id) {
    // era no caso do id ser number
    // Long idLong = id.longValue();

    //Deletar no Banco de dados
    userDocRepository.deleteById(id);
  }
}
