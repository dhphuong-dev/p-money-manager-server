package com.example.moneymanagerbe.repository;

import com.example.moneymanagerbe.constant.ErrorMessage;
import com.example.moneymanagerbe.domain.entity.User;
import com.example.moneymanagerbe.exception.NotFoundException;
import com.example.moneymanagerbe.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  @Query("SELECT u FROM User u WHERE u.id = ?1")
  Optional<User> findById(String id);

  @Query("SELECT u FROM User u WHERE u.username = ?1")
  Optional<User> findByUsername(String username);

  default User getUser(UserPrincipal currentUser) {
    return findByUsername(currentUser.getUsername())
        .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_USERNAME,
            new String[]{currentUser.getUsername()}));
  }

}
