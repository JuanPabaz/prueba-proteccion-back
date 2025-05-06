package com.prueba.proteccion.services;

import com.prueba.proteccion.entities.RefreshToken;
import com.prueba.proteccion.exceptions.ExpiredRefreshTokenException;
import com.prueba.proteccion.repositories.RefreshTokenRepository;
import com.prueba.proteccion.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    @Value("${application.security.jwt.refresh-token-expiration}")
    private long refreshTokenExpire;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public RefreshToken createRefreshToken(String username){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findByUsername(username).get());
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusSeconds(refreshTokenExpire));

        return refreshTokenRepository.save(refreshToken);
    }



    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new ExpiredRefreshTokenException("El token de actualización " + token.getToken() +  " esta vencido. Inicia sesión de nuevo");
        }
        return token;
    }



    public Optional<RefreshToken> findByUsername(String username){
        return refreshTokenRepository.findByUsername(username);
    }

    public void DeleteRefreshToken(RefreshToken refreshToken){
        refreshTokenRepository.delete(refreshToken);
    }
}
