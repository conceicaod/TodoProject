package app.todoproject.controllers;

import app.todoproject.entity.Todo;
import app.todoproject.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TaskController {

    @Autowired
    TodoService todoService;

    @RequestMapping("/addTask")
    public String handleAddTaskRequest(@RequestParam("todoTask") String taskContent, Model model){

        taskContent = taskContent.trim();
        Todo task = new Todo(taskContent, false);
        if(!taskContent.isEmpty() || !taskContent.isBlank()){
            todoService.saveTodoTask(task);
        }
        // TODO: add a call to get all tasks and send them back to the frontend
        List<Todo> allCurrentTasks = todoService.getAllTasks();
        model.addAttribute("tasks", allCurrentTasks);
        return "redirect:/";
    }
    @RequestMapping(value={"/", "/home", "/index"})
    public String loadHomePage(Model model){
        List<Todo> allCurrentTasks = todoService.getAllTasks();
        model.addAttribute("tasks", allCurrentTasks);
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
}
