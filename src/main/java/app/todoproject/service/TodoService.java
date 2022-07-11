package app.todoproject.service;

import app.todoproject.entity.Todo;
import app.todoproject.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    TodoRepository todoRepository;

    public void saveTodoTask(Todo todoTask){
        todoRepository.save(todoTask);
        // insert into Todo (id, content, complete) values (todoTask.getID(), todoTask.getContent(), todoTask.getComplete());
    }

    public List<Todo> getAllTasks(){
        return todoRepository.findAll();
    }

    public void markComplete(int id){
        Todo task = todoRepository.findById(id).get();
        if(task.isComplete() == true){
            task.setComplete(false);
        }
        else{
            task.setComplete(true);
        }

       todoRepository.save(task);
    }

    public void removeTask(int id){
        Todo task = todoRepository.findById(id).get();
        if(task.isComplete() == true){
            todoRepository.delete(task);
        }
    }

}
