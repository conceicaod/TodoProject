package app.todoproject.controllers;

import app.todoproject.entity.Todo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaskController {

    @RequestMapping("/addTask")
    public String handleAddTaskRequest(@RequestParam("todoTask") String taskContent){

        Todo task = new Todo(taskContent, false);

        return "index";
    }
}
