package thread;

import javafx.application.Platform;
import view.MainGui;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

public class ShutdownSystem extends SwingWorker<Boolean, Void> {
    private volatile  static ShutdownSystem uniqueInstance;
    private long shutdownTime;

    public static ShutdownSystem getInstance() {
        if (uniqueInstance == null) {
            synchronized (MainGui.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new ShutdownSystem();
                }
            }
        }
        return uniqueInstance;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        try {
            if (shutdownTime < 0)
                throw new InterruptedException();
            else {
                Thread.sleep(shutdownTime);
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
                    Platform.exit();
                });
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void setShutdown(String time) {	// set shutdown program
        int setTime = Integer.parseInt(time);

        this.shutdownTime = setTime * 1000 * 60;
    }
}
