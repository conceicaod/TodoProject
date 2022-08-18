package app.todoproject.entity;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String content;

    private boolean complete;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Todo(String content, boolean complete, User user) {
        this.content = content;
        this.complete = complete;
        this.user =user;
    }

    public Todo(String content, boolean complete) {
        this.content = content;
        this.complete = complete;
        this.user =new User();
    }

    public Todo(){
        this.content = "";
        this.complete = false;
        this.user = new User();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
