package app.todoproject.entity;

import net.bytebuddy.build.Plugin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="app_users")
public class User implements UserDetails {

    public enum Role{
        USER,
        ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private int id;

    private String username;

    private String password;

    @Transient
    private boolean accountNonExpired;
    @Transient
    private boolean accountNonLocked;
    @Transient
    private  boolean credentialsNonExpired;
    @Transient
    private boolean isEnabled;

    @OneToMany(mappedBy="user")
    private List<Todo> userTasks;

    public User(){
        this.username = "";
        this.password = "";
        this.accountNonExpired = true;
        this.isEnabled = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.userTasks = new ArrayList<>();
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.accountNonExpired = true;
        this.isEnabled = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.userTasks = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(Role.USER.toString()));
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Todo> getUserTasks() {
        return userTasks;
    }

    public void setUserTasks(List<Todo> userTasks) {
        this.userTasks = userTasks;
    }

    public void addTaskToList(Todo todoTask){
        userTasks.add(todoTask);
    }
}
