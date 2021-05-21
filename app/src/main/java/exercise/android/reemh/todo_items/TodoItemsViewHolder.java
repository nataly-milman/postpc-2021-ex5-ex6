package exercise.android.reemh.todo_items;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TodoItemsViewHolder extends RecyclerView.ViewHolder{
    TextView taskDescription;
    CheckBox checkBox;
    ImageView deleteButton;

    public TodoItemsViewHolder(@NonNull View itemView) {
        super(itemView);
        this.taskDescription = itemView.findViewById(R.id.taskDescription);
        this.checkBox = itemView.findViewById(R.id.markCheckbox);
        this.deleteButton = itemView.findViewById(R.id.deleteButton);
    }
}