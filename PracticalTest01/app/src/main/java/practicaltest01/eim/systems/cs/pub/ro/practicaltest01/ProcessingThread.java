package practicaltest01.eim.systems.cs.pub.ro.practicaltest01;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.sql.Date;
import java.util.Random;

/**
 * Created by monica on 30.03.2018.
 */

public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;

    private Random random = new Random();

    private double arithmeticMean;
    private double geometricMean;

    private int firstNumber;
    private int secondNumber;

    private final int act1 = 1;
    private final int act2 = 2;
    private final int act3 = 3;


    public ProcessingThread(Context context, int firstNumber, int secondNumber) {
        Log.d("[Thread]", "started");
        arithmeticMean = (firstNumber + secondNumber) / 2;
        geometricMean = Math.sqrt(firstNumber * secondNumber);
        this.context = context;
    }

    @Override
    public void run() {
        Log.d("[Thread]", "running");
        while (isRunning) {
            sendMessage();
            sleepTen();
        }
    }

    private void sendMessage(){
        Intent intent = new Intent();
        intent.setAction(Constants.actionTypes.get(random.nextInt(Constants.actionTypes.size())));
        intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + arithmeticMean + " " + geometricMean);
        context.sendBroadcast(intent);
    }

    private void sleepTen() {
        try {
            sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
