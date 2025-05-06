package com.prueba.proteccion.mappers;

import com.prueba.proteccion.dtos.TaskResponseDTO;
import com.prueba.proteccion.entities.Task;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TaskMapper {

    private final UserMapper userMapper;

    public TaskMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public TaskResponseDTO mapTask(Task task) {
        if (task == null) {
            return null;
        }

        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();
        taskResponseDTO.setId(task.getId());
        taskResponseDTO.setDescription(task.getDescription());
        taskResponseDTO.setTitle(task.getTitle());
        taskResponseDTO.setDueDate(task.getDueDate());
        taskResponseDTO.setState(task.getState());
        taskResponseDTO.setAssignedTo(userMapper.mapUsuario(task.getAssignedTo()));
        taskResponseDTO.setCreatedBy(userMapper.mapUsuario(task.getCreatedBy()));

        return taskResponseDTO;
    }

    public List<TaskResponseDTO> mapTaskList(List<Task> taskList) {
        return taskList.stream()
                .map(this::mapTask)
                .toList();
    }
}
