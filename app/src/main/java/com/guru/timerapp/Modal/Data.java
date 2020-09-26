package com.guru.timerapp.Modal;

public class Data {
    private int AlarmId;
    private String Time;

    public Data(int alarmId, String time) {
        AlarmId = alarmId;
        Time = time;
    }

    public Data() {
    }

    public int getAlarmId() {
        return AlarmId;
    }

    public void setAlarmId(int alarmId) {
        AlarmId = alarmId;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
