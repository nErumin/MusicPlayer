package thread;

import view.AlarmShowGui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmSystem extends Thread {    // alarm with thread to run under main program

    private long ringring;
    private String ampm;
    private String hour;
    private String minute;
    private String alarmText;

    public AlarmSystem(String ampm, String hour, String minute, String alarmText) {
        this.ampm = ampm;
        this.hour = hour;
        this.minute = minute;
        this.alarmText = alarmText;
    }

    @Override
    public void run() {    // to run
        try {
            if (ringring < 0)
                throw new InterruptedException();
            else {
                Thread.sleep(ringring);
                AlarmShowGui alarmShowGui = new AlarmShowGui(ampm, hour, minute, alarmText);
                alarmShowGui.showAndWait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setAlarm(String ampm, String hour, String min) {    // set alarm with ampm, hour, time

        int setHour = Integer.parseInt(hour);
        int setMin = Integer.parseInt(min);

        Calendar today = Calendar.getInstance();
        Calendar alarmTime = Calendar.getInstance();
        Calendar currentTime = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date = today.getTime();
        String Today = new SimpleDateFormat("yyyyMMdd").format(date);
        try {
            today.setTime(formatter.parse(Today));
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        alarmTime.set(Calendar.SECOND, +0);
        alarmTime.set(Calendar.MINUTE, +setMin);

        if (ampm.equals("오전")) {
            if (setHour != 12)
                alarmTime.set(Calendar.HOUR_OF_DAY, +setHour);
            else
                alarmTime.set(Calendar.HOUR_OF_DAY, +0);
        } else if (ampm.equals("오후")) {
            if (setHour != 12)
                alarmTime.set(Calendar.HOUR_OF_DAY, +setHour + 12);
            else
                alarmTime.set(Calendar.HOUR_OF_DAY, +12);
        }

        ringring = alarmTime.getTimeInMillis() - currentTime.getTimeInMillis();

    }
}
