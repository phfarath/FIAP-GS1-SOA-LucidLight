package service.dto;

// Example DTO for User
public class UserDTO {
    private String id; // Optional, might be assigned by the server
    private String username;
    private String email;
    private String role; // e.g., ADMIN, OPERATOR, TECHNICIAN
    // Avoid including password here for security reasons

    // Constructors, Getters, Setters
    public UserDTO() {
    }

    public UserDTO(String username, String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
               "id=\'" + id + "\\'" +
               ", username=\'" + username + "\\'" +
               ", email=\'" + email + "\\'" +
               ", role=\'" + role + "\\'" +
               "}";
    }
}

