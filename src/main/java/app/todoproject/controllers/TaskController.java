package app.todoproject.controllers;

import app.todoproject.entity.Todo;
import app.todoproject.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TaskController {

    @Autowired
    TodoService todoService;

    @RequestMapping("/addTask")
    public String handleAddTaskRequest(@RequestParam("todoTask") String taskContent, Model model){

        Todo task = new Todo(taskContent, false);
        todoService.saveTodoTask(task);
        // TODO: add a call to get all tasks and send them back to the frontend
        List<Todo> allCurrentTasks = todoService.getAllTasks();
        model.addAttribute("tasks", allCurrentTasks);
        return "index";
    }
}
