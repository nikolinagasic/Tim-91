package rs.zis.app.zis.auth;

@SuppressWarnings("SpellCheckingInspection")
public class JwtAuthenticationRequest {             // ono sto primam kad se neko loguje

    private String username;            // mail
    private String password;            // lozinka

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
