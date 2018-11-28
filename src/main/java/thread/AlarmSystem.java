package thread;

import javafx.application.Platform;
import view.AlarmShowGui;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class AlarmSystem extends SwingWorker<Boolean, Void> {    // alarm with thread to run under main program

    private long ringring;
    private String ampm;
    private int hour;
    private int minute;
    private String alarmText;

    public AlarmSystem(String ampm, int hour, int minute, String alarmText) {
        this.ampm = ampm;
        this.hour = hour;
        this.minute = minute;
        this.alarmText = alarmText;

        setAlarm();
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        try {
            if (ringring < 0)
                throw new InterruptedException();
            else {
                Thread.sleep(ringring);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected  void done(){
        Boolean status;
        try {
            status = get();
            if(status) {
                Platform.runLater(()->{
                    AlarmShowGui alarmShowGui = new AlarmShowGui(ampm, hour, minute, alarmText);
                    alarmShowGui.showAndWait();
                });
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void setAlarm() {    // set alarm with ampm, hour, time

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

        alarmTime.set(Calendar.SECOND, + 0);
        alarmTime.set(Calendar.MINUTE, + minute);

        if (ampm.equals("A.M.")) {
            if (hour != 12)
                alarmTime.set(Calendar.HOUR_OF_DAY, + hour);
            else
                alarmTime.set(Calendar.HOUR_OF_DAY, +0);
        } else if (ampm.equals("P.M.")) {
            if (hour != 12)
                alarmTime.set(Calendar.HOUR_OF_DAY, + hour + 12);
            else
                alarmTime.set(Calendar.HOUR_OF_DAY, +12);
        }

        ringring = alarmTime.getTimeInMillis() - currentTime.getTimeInMillis();
    }
}
