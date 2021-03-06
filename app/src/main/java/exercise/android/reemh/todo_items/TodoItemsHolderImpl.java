package exercise.android.reemh.todo_items;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class TodoItemsHolderImpl implements TodoItemsHolder {
  private List<TodoItem> todoItems = new LinkedList<>();

  @Override
  public List<TodoItem> getCurrentItems() {
    return this.todoItems;
  }

  @Override
  public void addNewInProgressItem(String description) {
    TodoItem newItem = new TodoItem();
    newItem.setDescription(description);
    newItem.setDone(false);
    this.todoItems.add(newItem);
    this.sortTodoItems();
  }

  @Override
  public void markItemDone(TodoItem item) {
    item.setDone(true);
    this.sortTodoItems();
  }

  @Override
  public void markItemInProgress(TodoItem item) {
    item.setDone(false);
    this.sortTodoItems();
  }

  @Override
  public void editItem(TodoItem item){
    for (int i = 0; i < this.todoItems.size(); i++){
      if (this.todoItems.get(i).getId() == item.getId()){
        todoItems.set(i, item);
        break;
      }
    }
    this.sortTodoItems();
  }

  @Override
  public void deleteItem(TodoItem item) {
    this.todoItems.remove(item);
    if (this.todoItems.size() == 0){
      TodoItem.resetUniqueId();
    }
  }

  public void sortTodoItems(){
    Comparator<TodoItem> compareTodoItems = (o1, o2) -> {

      if (!o1.isDone()){
        if (o2.isDone()){
          // o1 in progress, o2 done ( o1 > o2 )
          return 1;
        } else {
          // both in progress
          return o1.getCreationTime().compareTo(o2.getCreationTime());
        }
      }
      if (!o2.isDone()) {
        // o2 in progress, o1 done
        return -1;
      }
      // both done
      return 0;
    };

    Collections.sort(this.todoItems, Collections.reverseOrder(compareTodoItems));
  }

  public void setTodoItems(List<TodoItem> todoItems) {
    this.todoItems = todoItems;
  }
}
