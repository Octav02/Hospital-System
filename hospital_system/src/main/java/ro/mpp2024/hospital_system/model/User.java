package ro.mpp2024.hospital_system.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Identifiable<Long>, Serializable {
    @Id
    @GeneratedValue(generator = "increment")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "cnp")
    private String cnp;
    @Column(name = "password")
    private String password;
    @Column(name = "user_type")
    private UserType userType;
    @Column(name = "username")
    private String username;

    public User() {
        id = 0L;
    }

    public User(String firstName, String lastName, String cnp, String password, UserType userType, String username) {
        id = 0L;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
        this.password = password;
        this.userType = userType;
        this.username = username;
    }

    public User(Long id, String firstName, String lastName, String cnp, String password, UserType userType, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
        this.password = password;
        this.userType = userType;
        this.username = username;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cnp='" + cnp + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;

        return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(cnp, user.cnp) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(firstName);
        result = 31 * result + Objects.hashCode(lastName);
        result = 31 * result + Objects.hashCode(cnp);
        result = 31 * result + Objects.hashCode(password);
        return result;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
