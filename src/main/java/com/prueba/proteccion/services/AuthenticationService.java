package com.prueba.proteccion.services;

import com.prueba.proteccion.dtos.RegisterRequestDTO;
import com.prueba.proteccion.dtos.UserResponseDTO;
import com.prueba.proteccion.entities.User;
import com.prueba.proteccion.enums.Role;
import com.prueba.proteccion.exceptions.BadUserCredentialsException;
import com.prueba.proteccion.exceptions.ObjectNotFoundException;
import com.prueba.proteccion.mappers.UserMapper;
import com.prueba.proteccion.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtServiceImpl jwtService;

    private final UserMapper mapUser;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                 JwtServiceImpl jwtService, UserMapper mapUser) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.mapUser = mapUser;
    }

    public UserResponseDTO saveUser(RegisterRequestDTO registerRequestDTO) throws BadUserCredentialsException {
        if (userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()){
            throw new BadUserCredentialsException("Ya existe un usuario con este correo: "+ registerRequestDTO.getUsername() + ".");
        }

        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d/*\\-_.º!?¿'¡#!$%&]{6,}$";
        if (!registerRequestDTO.getPassword().matches(passwordRegex)){
            throw new BadUserCredentialsException("La contraseña debe tener al menos 6 caracteres y contener al menos una letra y un número.");
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!registerRequestDTO.getUsername().matches(emailRegex)){
            throw new BadUserCredentialsException("El correo no es valido.");
        }

        registerRequestDTO.setRole(Role.ADMIN);
        registerRequestDTO.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));

        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setPassword(registerRequestDTO.getPassword());
        user.setRole(registerRequestDTO.getRole());
        user.setIdNumber(registerRequestDTO.getIdNumber());
        user.setAddress(registerRequestDTO.getAddress());
        user.setFullName(registerRequestDTO.getFullName());

        return mapUser.mapUsuario(userRepository.save(user));
    }

    public String generateToken(String username) throws ObjectNotFoundException {
        return jwtService.generateToken(username);
    }

    public Map<String, Object> validateToken(String token){
        return jwtService.validateToken(token);
    }
}
