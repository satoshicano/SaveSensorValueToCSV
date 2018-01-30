package work.cano.savesensorvaluetocsv;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends Activity implements SensorEventListener {
    private final static String TAG = MainActivity.class.getSimpleName();
    private File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
    private FileWriter geomagnetic_fw, gyro_fw;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            File accel_file = new File(path, "geomagnetic-" + Utils.getNowDate() + ".csv");
            geomagnetic_fw = new FileWriter(accel_file, true);
            File gyro_file = new File(path, "gyro-" + Utils.getNowDate() + ".csv");
            gyro_fw = new FileWriter(gyro_file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sensor geomagnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        sensorManager.registerListener(this, geomagnetic, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            geomagnetic_fw.close();
            gyro_fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            Log.d(TAG, Float.toString(event.values[0]));

            try {
                String line = Float.toString(event.values[0]) +
                        "," + Float.toString(event.values[1]) +
                        "," + Float.toString(event.values[2]) +
                        "," + Utils.getNowDate() + "\n";
                geomagnetic_fw.write(line);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {

            try {
                String line = event.values[0] +
                        "," + event.values[1] +
                        "," + event.values[2] +
                        "," + Utils.getNowDate() + "\n";
                gyro_fw.write(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
