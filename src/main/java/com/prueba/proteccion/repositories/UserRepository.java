package com.prueba.proteccion.repositories;
import com.prueba.proteccion.entities.User;
import com.prueba.proteccion.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    //Try CI/CD
    @Query("SELECT u.role FROM User u WHERE u.username = :nombre")
    Role findRoleByUsername(String nombre);

}