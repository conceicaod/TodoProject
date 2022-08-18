package app.todoproject.service;

import app.todoproject.entity.Todo;
import app.todoproject.entity.User;
import app.todoproject.repository.TodoRepository;
import app.todoproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    public void saveUser(String username, String password){
        User user = new User(username, password);

        if(user.getUserTasks() != null){
            for(Todo task : user.getUserTasks()){
                todoRepository.save(task);
            }
        }
        userRepository.save(user);
    }

    public User retrieveUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }

    public List<User> getAllUsers(){
        return (List<User>) userRepository.findAll();
    }

}
