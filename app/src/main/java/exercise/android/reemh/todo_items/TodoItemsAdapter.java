package exercise.android.reemh.todo_items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TodoItemsAdapter extends RecyclerView.Adapter<TodoItemsViewHolder> {
    private TodoItemsHolder itemHolder;

    public TodoItemsAdapter(TodoItemsHolder itemHolder) {
        this.itemHolder = itemHolder;
    }

    @NonNull
    @Override
    public TodoItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_todo_item, parent, false);
        return new TodoItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoItemsViewHolder holder, int position) {
        TodoItem curItem = this.itemHolder.getCurrentItems().get(position);
        holder.taskDescription.setText(curItem.getDescription());
        holder.checkBox.setChecked(curItem.isDone());

        holder.checkBox.setOnClickListener(view -> {
            TodoItem item = itemHolder.getCurrentItems().get(holder.getLayoutPosition());
            if (holder.checkBox.isChecked()) {
                itemHolder.markItemDone(item);
            } else {
                itemHolder.markItemInProgress(item);
            }
            notifyDataSetChanged();
        });

        holder.deleteButton.setOnClickListener(view -> {
            itemHolder.deleteItem(curItem);
            notifyDataSetChanged();
        }
        );
    }

    @Override
    public int getItemCount() {
        return this.itemHolder.getCurrentItems().size();
    }
}