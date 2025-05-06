package com.prueba.proteccion.services;

import com.prueba.proteccion.dtos.TaskRequestDTO;
import com.prueba.proteccion.dtos.TaskResponseDTO;
import com.prueba.proteccion.entities.Task;
import com.prueba.proteccion.entities.User;
import com.prueba.proteccion.enums.State;
import com.prueba.proteccion.exceptions.BadCreateRequest;
import com.prueba.proteccion.exceptions.ObjectNotFoundException;
import com.prueba.proteccion.mappers.TaskMapper;
import com.prueba.proteccion.repositories.TaskRepository;
import com.prueba.proteccion.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userRepository = userRepository;
    }

    public List<TaskResponseDTO> findAllByUser(Long idUser) {
        return taskMapper.mapTaskList(taskRepository.findAllByUser(idUser));
    }

    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) throws ObjectNotFoundException {
        User userAssignedTo = userRepository.findById(taskRequestDTO.getAssignedToId())
                .orElseThrow(() -> new ObjectNotFoundException("Usuario asignado no encontrado"));

        User userAssignedBy = userRepository.findById(taskRequestDTO.getCreatedById())
                .orElseThrow(() -> new ObjectNotFoundException("Usuario asignador no encontrado"));

        if (taskRequestDTO.getTitle() == null || taskRequestDTO.getTitle().isEmpty()) {
            throw new BadCreateRequest("El titulo no puede estar vacio");
        }

        if (taskRequestDTO.getDescription() == null || taskRequestDTO.getDescription().isEmpty()) {
            throw new BadCreateRequest("La descripcion no pueda estar vacio");
        }

        if (taskRequestDTO.getDueDate() == null) {
            throw new BadCreateRequest("La tarea tiene que tener una fecha");
        }

        Task task = new Task();
        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setDueDate(taskRequestDTO.getDueDate());
        task.setAssignedTo(userAssignedTo);
        task.setCreatedBy(userAssignedBy);
        task.setState(State.TO_DO);

        return taskMapper.mapTask(taskRepository.save(task));
    }

    public TaskResponseDTO updateTask(Long idTask, TaskRequestDTO taskRequestDTO, Integer idUser) throws ObjectNotFoundException {
        taskRepository.findById(idTask)
                .orElseThrow(() -> new ObjectNotFoundException("La tarea no existe"));

        if (!Objects.equals(taskRequestDTO.getAssignedToId(), idUser) || !Objects.equals(taskRequestDTO.getCreatedById(), idUser)) {
            throw new BadCreateRequest("El usuario no es el asignado a la tarea ni el creador de esta");
        }

        return createTask(taskRequestDTO);
    }
}
