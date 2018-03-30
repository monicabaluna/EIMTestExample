package practicaltest01.eim.systems.cs.pub.ro.practicaltest01;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {

    private EditText leftText = null;
    private EditText rightText = null;
    private Button leftButton = null;
    private Button rightButton = null;
    private Button secondaryActivityButton = null;
    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private int serviceStatus;
    private final static int SECONDARY_ACTIVITY_REQUEST_CODE = 1;
    private IntentFilter intentFilter = new IntentFilter();
    private MyReceiver messageBroadcastReceiver = new MyReceiver();

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int leftNumberOfClicks = Integer.parseInt(leftText.getText().toString());
            int rightNumberOfClicks = Integer.parseInt(rightText.getText().toString());
            int numberOfClicks = leftNumberOfClicks + rightNumberOfClicks;

            switch (view.getId()) {
                case R.id.button1:
                    leftNumberOfClicks += 1;
                    leftText.setText(String.valueOf(leftNumberOfClicks));
                    break;
                case R.id.button2:
                    rightNumberOfClicks += 1;
                    rightText.setText(String.valueOf(rightNumberOfClicks));
                    break;
                case R.id.navigate_button:
                    Intent secondaryIntent = new Intent(getApplicationContext(),
                            PracticalTest01SecondaryActivity.class);
                    secondaryIntent.putExtra("numberOfClicks", numberOfClicks);
                    startActivityForResult(secondaryIntent, SECONDARY_ACTIVITY_REQUEST_CODE);
                    break;
            }

            if (numberOfClicks > Constants.MIN_CLICKS
                    && serviceStatus == Constants.SERVICE_STOPPED) {
                Intent broadcastIntent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                broadcastIntent.putExtra("firstNumber", leftNumberOfClicks);
                broadcastIntent.putExtra("secondNumber", rightNumberOfClicks);
                getApplicationContext().startService(broadcastIntent);
                serviceStatus = Constants.SERVICE_STARTED;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        for (int index = 0; index < Constants.actionTypes.size(); index++) {
            intentFilter.addAction(Constants.actionTypes.get(index));
        }

        leftText = findViewById(R.id.edit_text1);
        rightText = findViewById(R.id.edit_text2);
        leftButton = findViewById(R.id.button1);
        rightButton = findViewById(R.id.button2);
        secondaryActivityButton = findViewById(R.id.navigate_button);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("leftValue")) {
                leftText.setText(savedInstanceState.getString("leftValue"));
            } else {
                leftText.setText(String.valueOf(0));
            }
            if (savedInstanceState.containsKey("rightValue")) {
                rightText.setText(savedInstanceState.getString("rightValue"));
            } else {
                rightText.setText(String.valueOf(0));
            }
        } else {
            leftText.setText(String.valueOf(0));
            rightText.setText(String.valueOf(0));
        }

        secondaryActivityButton.setOnClickListener(buttonClickListener);
        leftButton.setOnClickListener(buttonClickListener);
        rightButton.setOnClickListener(buttonClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageBroadcastReceiver);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("leftValue", leftText.getText().toString());
        outState.putString("rightValue", rightText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);if (savedInstanceState.containsKey("leftValue")) {
            leftText.setText(savedInstanceState.getString("leftValue"));
        } else {
            leftText.setText(String.valueOf(0));
        }
        if (savedInstanceState.containsKey("rightValue")) {
            rightText.setText(savedInstanceState.getString("rightValue"));
        } else {
            rightText.setText(String.valueOf(0));
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }
}
