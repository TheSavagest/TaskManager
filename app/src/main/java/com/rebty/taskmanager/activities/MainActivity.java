package com.rebty.taskmanager.activities;

import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.rebty.taskmanager.Classes.RecyclerAdapter;
import com.rebty.taskmanager.Classes.Task;
import com.rebty.taskmanager.Classes.WorkXML;
import com.rebty.taskmanager.Fragments.DeleteDialog;
import com.rebty.taskmanager.Fragments.NewDialog;
import com.rebty.taskmanager.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Task> listOfTasks = new ArrayList<>();
    private static RecyclerView recyclerViewOfTasks;
    DialogFragment addDialog, deleteDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewOfTasks = (RecyclerView) findViewById(R.id.recyclerView_ofTasks);
        recyclerViewOfTasks.setLayoutManager(new LinearLayoutManager(this));

        addDialog = new NewDialog();
        deleteDialog = new DeleteDialog();

        if (!readFile()) {
            listOfTasks.add(new Task("Поблагодарить разработчика", "Спасибо, что скачали наше приложение. Просим оценить его по достоинству.", Color.RED, new Date().getTime()));
            writeFile();
        }
        initializeAdapter(listOfTasks);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add: {
                addDialog.show(getFragmentManager(), "add");
                break;
            }
            case R.id.action_delete: {
                deleteDialog.show(getFragmentManager(), "delete");
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void initializeAdapter(ArrayList<Task> list) {
        recyclerViewOfTasks.setAdapter(new RecyclerAdapter(list));
    }

    void writeFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput("base.xml", MODE_PRIVATE)));
            bw.write(WorkXML.writeXML(listOfTasks));
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean readFile() {
        try {
            listOfTasks = new ArrayList<>();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput("base.xml")));
            String str = "";
            while ((str = br.readLine()) != null) {
                listOfTasks = WorkXML.readXML(str);
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void sortListOfTasks() {
        Task bufferTask;
        ArrayList<Task> bufferListOfTasks = new ArrayList<>();
        int iMax = listOfTasks.size();
        for (int i = 0; i < iMax; i++) {
            bufferTask = listOfTasks.get(0);
            for (int j = 0; j < listOfTasks.size(); j++) {
                if ((bufferTask.longTime > listOfTasks.get(j).longTime)) {
                    bufferTask = listOfTasks.get(j);
                }
            }
            listOfTasks.remove(listOfTasks.indexOf(bufferTask));
            bufferListOfTasks.add(bufferTask);
        }
        listOfTasks = bufferListOfTasks;
        initializeAdapter(listOfTasks);
    }

    @Override
    public void onBackPressed() {
        writeFile();
        super.onBackPressed();
    }

    @Override
    protected void onUserLeaveHint() {
        writeFile();
        super.onUserLeaveHint();
    }

    @Override
    protected void onPause() {
        writeFile();
        super.onPause();
    }

    @Override
    protected void onStop() {
        writeFile();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        writeFile();
        super.onDestroy();
    }
}
