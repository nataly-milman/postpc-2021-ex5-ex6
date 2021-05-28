package exercise.android.reemh.todo_items;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

  public TodoItemsHolder holder = null;
  private TodoItemsAdapter adapter;
  private EditText editItem;
  private ApplicationClass application;
  private BroadcastReceiver editBroadcastReceiver;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    application = (ApplicationClass) getApplicationContext();
    if (holder == null) {
      holder = new TodoItemsHolderImpl();
    }

    application.loadTodoItems();
    this.adapter = new TodoItemsAdapter(holder, this);
    RecyclerView recyclerView = findViewById(R.id.recyclerTodoItemsList);
    recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    recyclerView.setAdapter(adapter);

    this.editItem = findViewById(R.id.editTextInsertTask);
    FloatingActionButton createItem = findViewById(R.id.buttonCreateTodoItem);

    createItem.setOnClickListener(v -> {
      String taskDescription = editItem.getText().toString();
      if (taskDescription.equals("")){
        return;
      }
      this.holder.addNewInProgressItem(taskDescription);
      this.editItem.setText("");
      this.adapter.notifyDataSetChanged();
    });

    editBroadcastReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("editItem")) {
          TodoItem changedItem = (TodoItem) intent.getSerializableExtra("Item");
          holder.editItem(changedItem);
          application.setTodoItems(holder.getCurrentItems());
          application.saveTodoItems();
          adapter.notifyDataSetChanged();
        }
      }
    };
    registerReceiver(editBroadcastReceiver, new IntentFilter("editItem"));

  }

  @Override
  protected void onStop(){
    super.onStop();
    application.setTodoItems(this.holder.getCurrentItems());
    application.saveTodoItems();
  }

  @Override
  protected void onResume(){
    super.onResume();
    application.loadTodoItems();
    holder.setTodoItems(application.getTodoItems());
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putSerializable("Holder", this.holder);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    this.unregisterReceiver(editBroadcastReceiver);
  }

}

/*

SPECS:

- the screen starts out empty (no items shown, edit-text input should be empty)
- every time the user taps the "add TO-DO item" button:
    * if the edit-text is empty (no input), nothing happens
    * if there is input:
        - a new TodoItem (checkbox not checked) will be created and added to the items list
        - the new TodoItem will be shown as the first item in the Recycler view
        - the edit-text input will be erased
- the "TodoItems" list is shown in the screen
  * every operation that creates/edits/deletes a TodoItem should immediately be shown in the UI
  * the order of the TodoItems in the UI is:
    - all IN-PROGRESS items are shown first. items are sorted by creation time,
      where the last-created item is the first item in the list
    - all DONE items are shown afterwards, no particular sort is needed (but try to think about what's the best UX for the user)
  * every item shows a checkbox and a description. you can decide to show other data as well (creation time, etc)
  * DONE items should show the checkbox as checked, and the description with a strike-through text
  * IN-PROGRESS items should show the checkbox as not checked, and the description text normal
  * upon click on the checkbox, flip the TodoItem's state (if was DONE will be IN-PROGRESS, and vice versa)
  * add a functionality to remove a TodoItem. either by a button, long-click or any other UX as you want
- when a screen rotation happens (user flips the screen):
  * the UI should still show the same list of TodoItems
  * the edit-text should store the same user-input (don't erase input upon screen change)

Remarks:
- you should use the `holder` field of the activity
- you will need to create a class extending from RecyclerView.Adapter and use it in this activity
- notice that you have the "row_todo_item.xml" file and you can use it in the adapter
- you should add tests to make sure your activity works as expected. take a look at file `MainActivityTest.java`



(optional, for advanced students:
- save the TodoItems list to file, so the list will still be in the same state even when app is killed and re-launched
)

*/
