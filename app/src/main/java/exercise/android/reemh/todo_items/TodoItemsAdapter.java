package exercise.android.reemh.todo_items;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TodoItemsAdapter extends RecyclerView.Adapter<TodoItemsViewHolder> {
    private final TodoItemsHolder itemHolder;
    Context context;
    LayoutInflater inflater;

    public TodoItemsAdapter(TodoItemsHolder itemHolder, Context context) {
        this.itemHolder = itemHolder;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TodoItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.inflater.inflate(R.layout.row_todo_item, parent, false);
        return new TodoItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoItemsViewHolder holder, int position) {
        TodoItem itemInEditing = this.itemHolder.getCurrentItems().get(position);
        holder.taskDescription.setText(itemInEditing.getDescription());
        holder.checkBox.setChecked(itemInEditing.isDone());

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
            itemHolder.deleteItem(itemInEditing);
            notifyDataSetChanged();
        }
        );

        holder.taskDescription.setOnClickListener(view ->{
            Intent editIntent = new Intent(this.context, TodoItemsEditActivity.class);
            editIntent.putExtra("Item", itemInEditing);
            this.context.startActivity(editIntent);
        });
    }

    @Override
    public int getItemCount() {
        return this.itemHolder.getCurrentItems().size();
    }
}