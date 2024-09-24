package micro.qa.microkafka.db.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.UUID;

public class UserJson {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("username")
    private String username;

    private transient String password;

    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("surname")
    private String surname;

    public UserJson() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserJson userJson = (UserJson) o;
        return Objects.equals(id, userJson.id) &&
                Objects.equals(username, userJson.username) &&
                Objects.equals(password, userJson.password) &&
                Objects.equals(firstname, userJson.firstname) &&
                Objects.equals(surname, userJson.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, firstname, surname);
    }

    @Override
    public String toString() {
        return "UserJson{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
