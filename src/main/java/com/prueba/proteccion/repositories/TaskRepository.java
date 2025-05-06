package com.prueba.proteccion.repositories;

import com.prueba.proteccion.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("SELECT t FROM Task t WHERE t.assignedTo.idUser = :idUser OR t.createdBy.idUser = :idUser")
    List<Task> findAllByUser(@Param("idUser") Long idUser);
}
