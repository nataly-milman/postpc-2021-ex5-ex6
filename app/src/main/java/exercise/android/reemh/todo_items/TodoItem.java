package exercise.android.reemh.todo_items;

import java.io.Serializable;
import java.util.Date;

public class TodoItem implements Serializable {
    private String description;
    private boolean isDone;
    private final Date creationTime;
    private Date lastEditTime;
    private static long uniqueIds = 0;
    private final long id;

    public TodoItem(){
        this.id = uniqueIds + 1;
        uniqueIds += 1;
        this.description = "";
        this.isDone = false;
        this.creationTime = new Date();
        this.lastEditTime = this.creationTime;
    }

    public long getId() {
        return id;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        this.lastEditTime = new Date();
        isDone = done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.lastEditTime = new Date();
        this.description = description + " "+this.id + "/" + uniqueIds; //TODO remove test
    }

}
