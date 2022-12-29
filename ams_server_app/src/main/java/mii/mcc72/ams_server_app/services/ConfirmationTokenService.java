/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mii.mcc72.ams_server_app.services;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.ConfirmationToken;
import mii.mcc72.ams_server_app.repos.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author bintang mada
 */
@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}
