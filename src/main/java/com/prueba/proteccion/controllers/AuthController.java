package com.prueba.proteccion.controllers;

import com.prueba.proteccion.dtos.AuthRequestDTO;
import com.prueba.proteccion.dtos.AuthResponseDTO;
import com.prueba.proteccion.dtos.RegisterRequestDTO;
import com.prueba.proteccion.dtos.UserResponseDTO;
import com.prueba.proteccion.entities.RefreshToken;
import com.prueba.proteccion.exceptions.BadUserCredentialsException;
import com.prueba.proteccion.repositories.UserRepository;
import com.prueba.proteccion.services.AuthenticationService;
import com.prueba.proteccion.services.JwtServiceImpl;
import com.prueba.proteccion.services.RefreshTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type"})
public class AuthController {

    private final AuthenticationService authenticationService;

    private final RefreshTokenService refreshTokenService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    public AuthController(AuthenticationService authenticationService, RefreshTokenService refreshTokenService,
                          AuthenticationManager authenticationManager,
                          UserRepository userRepository) {
        this.authenticationService = authenticationService;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public UserResponseDTO addNewUser(@RequestBody RegisterRequestDTO registerRequestDTO){
        return authenticationService.saveUser(registerRequestDTO);
    }

    @PostMapping("/login")
    public AuthResponseDTO getToken(@RequestBody AuthRequestDTO authRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            Optional<RefreshToken> refreshTokenOptional = refreshTokenService.findByUsername(authRequest.getUsername());
            refreshTokenOptional.ifPresent(refreshTokenService::DeleteRefreshToken);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());
            AuthResponseDTO authResponseDTO = new AuthResponseDTO();
            authResponseDTO.setRole(userRepository.findRoleByUsername(authRequest.getUsername()));
            authResponseDTO.setRefreshToken(refreshToken.getToken());
            authResponseDTO.setAccessToken(authenticationService.generateToken(authRequest.getUsername()));

            return authResponseDTO;

        }catch (BadUserCredentialsException e){
            throw new BadUserCredentialsException(e.getMessage());
        } catch (Exception e){
            throw new BadUserCredentialsException("Usuario y/o contraseña incorrectas");
        }

    }

//    @PostMapping("/refreshToken")
//    public AuthResponseDTO refreshToken(@RequestBody AuthResponseDTO authResponseDTO){
//
//        return refreshTokenService.findByToken(authResponseDTO.getRefreshToken())
//                .map(refreshTokenService::verifyExpiration)
//                .map(RefreshToken::getUser)
//                .map(userCredential -> {
//                    String accessToken = null;
//                    try {
//                        accessToken = jwtService.generateToken(userCredential.getUsername());
//                    } catch (ObjectNotFoundException e) {
//                        throw new RuntimeException(e);
//                    }
//
//                    AuthResponseDTO authResponseDTO2 = new AuthResponseDTO();
//                    authResponseDTO2.setAccessToken(accessToken);
//                    authResponseDTO2.setRefreshToken(authResponseDTO.getRefreshToken());
//
//                    return AuthResponseDTO.builder()
//                            .accessToken(accessToken)
//                            .refreshToken(authResponseDTO.getRefreshToken()).build();
//                }).orElseThrow(() ->new ExpiredRefreshTokenException("El refresh token no se encuentra en la base de datos"));
//    }

    @GetMapping("/validateToken/{token}")
    public Map<String, Object> validateToken(@PathVariable(name = "token") String token){
        return authenticationService.validateToken(token);
    }

}
