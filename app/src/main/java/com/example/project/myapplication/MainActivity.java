package com.example.project.myapplication;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.myapplication.DBHandler.DatabaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    ArrayAdapter<String> mAdapter;
    ListView lstTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        lstTask = (ListView)findViewById(R.id.lstTask);
         loadTaskList();
    }
        private void loadTaskList() {
            ArrayList<String> taskList = db.getTaskList();
            if(mAdapter==null){
                mAdapter = new ArrayAdapter<String>(this,R.layout.row,R.id.task_title,taskList);
                lstTask.setAdapter(mAdapter);
            }
            else{
                mAdapter.clear();
                mAdapter.addAll(taskList);
                mAdapter.notifyDataSetChanged();
            }
        }
    public void deleteTask(View view){

        View parent = (View)view.getParent();
        TextView taskTextView = (TextView)parent.findViewById(R.id.task_title);
        Log.e("String", (String) taskTextView.getText());
        String task = String.valueOf(taskTextView.getText());
        db.deleteTask(task);
        loadTaskList();
        Toast.makeText(MainActivity.this,"Work done",Toast.LENGTH_LONG).show();
    }
    public void additem(View view){
        final EditText taskEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("New Task")
                .setMessage("What's next?")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         String task = String.valueOf(taskEditText.getText());

                         boolean test = db.insertNewTask(task);


                        if (test){
                            loadTaskList();
                            Toast.makeText(MainActivity.this, "Item Added", Toast.LENGTH_SHORT).show();
                        }


                    }
                })
                .setNegativeButton("Cancel",null)
                .create();
        dialog.show();
    }
}
