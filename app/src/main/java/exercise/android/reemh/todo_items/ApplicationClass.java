package exercise.android.reemh.todo_items;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class ApplicationClass extends Application {
    private List<TodoItem> todoItems;
    SharedPreferences sharedPreferences;
    Context context;

    public ApplicationClass() {
        super();
    }

    public ApplicationClass(Context context){
        this.context=context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        loadTodoItems();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        loadTodoItems();
    }

    public void saveTodoItems() {
        String itemsJson = new Gson().toJson(todoItems);
        sharedPreferences.edit().putString("todoItems", itemsJson).apply();
    }

    public void loadTodoItems() {
        todoItems =  new LinkedList<>();
        String itemsJson = sharedPreferences.getString("todoItems", "");
        if (!itemsJson.isEmpty()) {
            Type listType = new TypeToken<LinkedList<TodoItem>>(){}.getType();
            todoItems = new Gson().fromJson(itemsJson, listType);
        }
    }

    public List<TodoItem> getTodoItems() {
        return todoItems;
    }

    public void setTodoItems(List<TodoItem> todoItems) {
        this.todoItems = todoItems;
    }
}
