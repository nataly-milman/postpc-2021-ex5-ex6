package exercise.android.reemh.todo_items;

import java.io.Serializable;
import java.util.Date;

public class TodoItem implements Serializable {
    // TODO: edit this class as you want
    private String description;
    private boolean isDone;
    private final Date creationTime;

    public TodoItem(){
        this.description = "";
        this.isDone = false;
        this.creationTime = new Date();
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
