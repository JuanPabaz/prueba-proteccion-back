package com.prueba.proteccion.controllers;

import com.prueba.proteccion.dtos.TaskRequestDTO;
import com.prueba.proteccion.dtos.TaskResponseDTO;
import com.prueba.proteccion.exceptions.ObjectNotFoundException;
import com.prueba.proteccion.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{idUser}")
    public List<TaskResponseDTO> findAllByUser(@PathVariable Long idUser) {
        return taskService.findAllByUser(idUser);
    }

    @PostMapping
    public TaskResponseDTO createTask(@RequestBody TaskRequestDTO taskRequestDTO) throws ObjectNotFoundException {
        return taskService.createTask(taskRequestDTO);
    }

    @PutMapping("/{idTask}/{idUser}")
    public TaskResponseDTO updateTask(@PathVariable("idTask") Long idTask,
                                      @PathVariable("idUser") Integer idUser,
                                      @RequestBody TaskRequestDTO taskRequestDTO) throws ObjectNotFoundException {
        return taskService.updateTask(idTask ,taskRequestDTO, idUser);
    }
}
