package com.prueba.proteccion.repositories;

import com.prueba.proteccion.entities.Task;
import com.prueba.proteccion.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("SELECT t FROM Task t WHERE (t.assignedTo.idUser = :idUser OR t.createdBy.idUser = :idUser) " +
            "AND (:state IS NULL OR t.state = :state) " +
            "AND (:title IS NULL OR LOWER(t.title) LIKE '%' || LOWER(:title) || '%')")
    List<Task> findAllByUserWithFilters(
            @Param("idUser") Long idUser,
            @Param("state") State state,
            @Param("title") String title
    );
}
