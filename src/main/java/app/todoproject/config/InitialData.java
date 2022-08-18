package app.todoproject.config;

import app.todoproject.entity.Todo;
import app.todoproject.entity.User;
import app.todoproject.service.TodoService;
import app.todoproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class InitialData {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    private TodoService todoService;

    @Autowired
    public InitialData(UserService userService, PasswordEncoder passwordEncoder, TodoService todoService){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.todoService = todoService;
    }

    @PostConstruct
    public void init(){
        User user = new User("test@gmail.com", passwordEncoder.encode("test"));

      Todo taskOne = new Todo("Deploy website", false, user);
       user.addTaskToList(taskOne);
       todoService.saveTodoTask(taskOne);

       List<Todo> tasks = new ArrayList<>();
       tasks.add(taskOne);
       user.setUserTasks(tasks);

        userService.saveUser(user.getUsername(), user.getPassword());

    }
}
