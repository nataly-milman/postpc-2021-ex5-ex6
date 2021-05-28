package exercise.android.reemh.todo_items;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TodoItemsEditActivity extends AppCompatActivity {
    private TextView taskEditTime;
    protected TodoItem item;
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yy K:mm a");
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat formatHour = new SimpleDateFormat("K:mm a");

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_todo_item);
        EditText taskDescription = findViewById(R.id.TaskDescriptionValue);
        CheckBox taskStatus = findViewById(R.id.DoneValue);
        TextView taskCreationTime = findViewById(R.id.creationTimeValue);
        this.taskEditTime = findViewById(R.id.ModificationTimeValue);

        Intent curIntent = getIntent();
        this.item = (TodoItem) curIntent.getSerializableExtra("Item");
        taskCreationTime.setText(formatDate.format(item.getCreationTime()));
        this.updateModifiedTime();
        taskDescription.setText(item.getDescription());
        taskStatus.setChecked(item.isDone());
        taskStatus.setOnCheckedChangeListener((btn, isChecked) -> {
            item.setDone(isChecked);
            this.updateModifiedTime();
        });

        taskDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                item.setDescription(s.toString());
                updateModifiedTime();
            }
        });
    }

    private void updateModifiedTime() {
        Date currentTime = new Date();
        long minutesSinceLastEdit = TimeUnit.MILLISECONDS.toMinutes(currentTime.getTime() -
                this.item.getLastEditTime().getTime());
        if (minutesSinceLastEdit < 60) {
            taskEditTime.setText(String.format("%s minutes ago", minutesSinceLastEdit));
        } else if (minutesSinceLastEdit < 60 * 24) {
            taskEditTime.setText(String.format("Today at %s",
                    this.formatHour.format(item.getLastEditTime())));
        } else {
            taskEditTime.setText(formatDate.format(this.item.getLastEditTime()));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent changed = new Intent("editItem");
        changed.putExtra("Item", this.item);
        sendBroadcast(changed);
    }
}