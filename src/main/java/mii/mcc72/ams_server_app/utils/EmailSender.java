/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mii.mcc72.ams_server_app.utils;

/**
 *
 * @author bintang mada
 */
public interface EmailSender {
    void send(String to, String subject, String email) throws InterruptedException;
}
