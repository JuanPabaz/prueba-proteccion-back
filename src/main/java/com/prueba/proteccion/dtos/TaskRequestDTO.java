package com.prueba.proteccion.dtos;

import com.prueba.proteccion.entities.User;
import com.prueba.proteccion.enums.State;

import java.time.LocalDate;

public class TaskRequestDTO {

    private String title;

    private String description;

    private State state;

    private LocalDate dueDate;

    private Integer assignedToId;

    private Integer createdById;

    public TaskRequestDTO() {
    }

    public TaskRequestDTO(String title, String description, State state, LocalDate dueDate, Integer assignedToId, Integer createdById) {
        this.title = title;
        this.description = description;
        this.state = state;
        this.dueDate = dueDate;
        this.assignedToId = assignedToId;
        this.createdById = createdById;
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

    public Integer getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Integer assignedToId) {
        this.assignedToId = assignedToId;
    }

    public Integer getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Integer createdById) {
        this.createdById = createdById;
    }
}
