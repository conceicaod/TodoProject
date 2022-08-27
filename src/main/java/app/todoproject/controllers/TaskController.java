package app.todoproject.controllers;

import app.todoproject.entity.Todo;
import app.todoproject.entity.User;
import app.todoproject.service.TodoService;
import app.todoproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    @Autowired
    TodoService todoService;

    @Autowired
    UserService userService;

    @RequestMapping("/addTask")
    public String handleAddTaskRequest(@RequestParam("todoTask") String taskContent, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        User user = userService.retrieveUserByUsername(currentUserName);

        taskContent = taskContent.trim();
        Todo task = new Todo(taskContent, false);
        task.setUser(user);
        if(!taskContent.isEmpty() || !taskContent.isBlank()){
            todoService.saveTodoTask(task);
        }
        user.addTaskToList(task);
        userService.saveUser(user);

        List<Todo> allCurrentTasks = user.getUserTasks();
        model.addAttribute("tasks", allCurrentTasks);
        return "redirect:/";
    }
    @RequestMapping(value={"/", "/home", "/index"})
    public String loadHomePage(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        User user = userService.retrieveUserByUsername(currentUserName);

        List<Todo> allCurrentTasks = user.getUserTasks();
        int count = 0;
        for(Todo task : allCurrentTasks){
            if(!task.isComplete()){
                count++;
            }
        }
        model.addAttribute("inCompleteCount", count);
        model.addAttribute("tasks", allCurrentTasks);
        model.addAttribute("currentUser", user.getUsername());
        return "index";
    }

    @RequestMapping("/markComplete/{id}")
    public String markTaskComplete(@PathVariable("id") int id){
        todoService.markComplete(id);
        return "redirect:/";
    }

    @RequestMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable("id") int id){
        todoService.removeTask(id);
        return "redirect:/";
    }

    @RequestMapping("/showIncompleteTasks")
    public String loadIncompleteTasks(Model model){
        List<Todo> allCurrentTasks = todoService.getAllTasks();
        List<Todo> incompleteTasks = new ArrayList<>();
        int count = 0;
        for(Todo task : allCurrentTasks){
            if(!task.isComplete()){
                incompleteTasks.add(task);
                count++;
            }
        }
        model.addAttribute("inCompleteCount", count);

        model.addAttribute("tasks", incompleteTasks);
        return "index";
    }
    @RequestMapping("/showCompleteTasks")
    public String loadCompleteTasks(Model model){
        List<Todo> allCurrentTasks = todoService.getAllTasks();
        List<Todo> completeTasks = new ArrayList<>();
        int count  = 0;
        for(Todo task : allCurrentTasks){
            if(task.isComplete()){
                completeTasks.add(task);
            }
            else{
                count++;
            }
        }
        model.addAttribute("inCompleteCount", count);
        model.addAttribute("tasks", completeTasks);
        return "index";
    }

    @RequestMapping("/clearCompletedTasks")
    public String clearComplete(Model model){
        List<Todo> allCurrentTasks = todoService.getAllTasks();
        for(Todo task : allCurrentTasks){
            if(task.isComplete()){
              todoService.removeTask(task.getId());
            }
        }
        return "redirect:/";
    }

}
