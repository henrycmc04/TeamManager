/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progetto.Server.dto.response;

/**
 *
 * @author enric
 */
public class LoginResponseDTO {
    
    private String token;
    private String email;
    private boolean haSocieta;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, String email, boolean haSocieta) {
        this.token = token;
        this.email = email;
        this.haSocieta = haSocieta;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public boolean isHaSocieta() {
        return haSocieta;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHaSocieta(boolean haSocieta) {
        this.haSocieta = haSocieta;
    }

    
    
}
