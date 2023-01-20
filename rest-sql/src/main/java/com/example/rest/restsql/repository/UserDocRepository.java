package com.example.rest.restsql.repository;

import com.example.rest.restsql.User.UserDocumentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDocRepository
  extends JpaRepository<UserDocumentation, Long> {}
