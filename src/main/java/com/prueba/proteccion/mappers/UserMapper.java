package com.prueba.proteccion.mappers;

import com.prueba.proteccion.dtos.UserResponseDTO;
import com.prueba.proteccion.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public UserResponseDTO mapUsuario(User user) {
        if (user == null) {
            return null;
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setIdUser(user.getIdUser());
        userResponseDTO.setFullName(user.getFullName());
        userResponseDTO.setRole(user.getRole());
        userResponseDTO.setUsername(user.getUsername());

        return userResponseDTO;
    }

    public List<UserResponseDTO> mapUsuarioList(List<User> userList) {
        return userList.stream()
                .map(this::mapUsuario)
                .toList();
    }
}
