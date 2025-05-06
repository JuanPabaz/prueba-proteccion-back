package com.prueba.proteccion.dtos;

import com.prueba.proteccion.entities.User;
import com.prueba.proteccion.enums.State;
import jakarta.persistence.*;

import java.time.LocalDate;

public class TaskResponseDTO {

    private Long id;

    private String title;

    private String description;

    private State state;

    private LocalDate dueDate;

    private UserResponseDTO assignedTo;

    private UserResponseDTO createdBy;

    public TaskResponseDTO() {
    }

    public TaskResponseDTO(Long id, String title, String description, State state, LocalDate dueDate, UserResponseDTO assignedTo, UserResponseDTO createdBy) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = state;
        this.dueDate = dueDate;
        this.assignedTo = assignedTo;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public UserResponseDTO getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(UserResponseDTO assignedTo) {
        this.assignedTo = assignedTo;
    }

    public UserResponseDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserResponseDTO createdBy) {
        this.createdBy = createdBy;
    }
}
