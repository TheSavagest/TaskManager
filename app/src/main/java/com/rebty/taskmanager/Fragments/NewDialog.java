package com.rebty.taskmanager.Fragments;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.rebty.taskmanager.activities.MainActivity;
import com.rebty.taskmanager.Classes.Task;
import com.rebty.taskmanager.R;

import java.util.Date;


public class NewDialog extends DialogFragment implements OnClickListener {

    int bufferYear, bufferMonth, bufferDay, bufferHour, bufferMinute, bufferIcon;
    EditText editTextTaskText, editTextTaskHeader;
    String bufferHeader, bufferText;
    Button buttonCreate, buttonSetDate, buttonsSetTime, buttonSetIcon;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_new, null);

        editTextTaskHeader = (EditText) v.findViewById(R.id.editTextTaskHeader);
        editTextTaskText = (EditText) v.findViewById(R.id.editTextTaskText);

        buttonSetDate = (Button) v.findViewById(R.id.buttonSetDate);
        buttonSetDate.setOnClickListener(this);
        buttonsSetTime = (Button) v.findViewById(R.id.buttonSetTime);
        buttonsSetTime.setOnClickListener(this);
        buttonSetIcon = (Button) v.findViewById(R.id.buttonSetIcon);
        buttonSetIcon.setBackgroundColor(Color.GREEN);
        buttonSetIcon.setOnClickListener(this);
        buttonCreate = (Button) v.findViewById(R.id.buttonCreate);
        buttonCreate.setOnClickListener(this);

        Date dateAndTime = new Date();
        bufferYear = dateAndTime.getYear();
        bufferMonth = dateAndTime.getMonth();
        bufferDay = dateAndTime.getDate();
        bufferHour = dateAndTime.getHours();
        bufferMinute = dateAndTime.getMinutes();
        bufferIcon = Color.GREEN;

        setInitialDate();
        setInitialTime();

        return v;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCreate:
                bufferHeader = editTextTaskHeader.getText().toString();
                bufferText = editTextTaskText.getText().toString();
                MainActivity.listOfTasks.add(new Task(bufferHeader, bufferText, bufferIcon, bufferYear, bufferMonth, bufferDay, bufferHour, bufferMinute));
                MainActivity.initializeAdapter(MainActivity.listOfTasks);
                MainActivity.sortListOfTasks();

                dismiss();
                break;
            case R.id.buttonSetDate:
                mSetDate(v);
                break;
            case R.id.buttonSetTime:
                mSetTime(v);
                break;
            case R.id.buttonSetIcon:
                switch (bufferIcon) {
                    case Color.GREEN:
                        bufferIcon = Color.YELLOW;
                        break;
                    case Color.YELLOW:
                        bufferIcon = Color.RED;
                        break;
                    case Color.RED:
                        bufferIcon = Color.GREEN;
                        break;
                }
                buttonSetIcon.setBackgroundColor(bufferIcon);
                break;
        }
    }

    // отображаем диалоговое окно для выбора даты
    public void mSetDate(View view) {
        new DatePickerDialog(getActivity(), onDateListener,
                bufferYear + 1900,
                bufferMonth,
                bufferDay)
                .show();
    }

    // отображаем диалоговое окно для выбора времени
    public void mSetTime(View view) {
        new TimePickerDialog(getActivity(), onTimeListener,
                bufferHour,
                bufferMinute, true)
                .show();
    }

    // установка начальной даты
    private void setInitialDate() {
        String bufferString = Integer.toString(bufferDay) + '.'
                + Integer.toString(bufferMonth + 1) + '.'
                + Integer.toString(bufferYear + 1900);
        buttonSetDate.setText(bufferString);
    }

    //установка начального времени
    private void setInitialTime() {
        String bufferString = Integer.toString(bufferHour) + ':';
        if (bufferMinute / 10 == 0) {
            bufferString = bufferString + '0' + Integer.toString(bufferMinute);
        } else {
            bufferString = bufferString + Integer.toString(bufferMinute);
        }
        buttonsSetTime.setText(bufferString);
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener onDateListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int month, int day) {
            bufferYear = year - 1900;
            bufferMonth = month;
            bufferDay = day;
            setInitialDate();
        }
    };

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener onTimeListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hour, int minute) {
            bufferHour = hour;
            bufferMinute = minute;
            setInitialTime();
        }
    };
}