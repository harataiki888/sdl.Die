package jp.ac.titech.itpro.sdl.die;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private final static String TAG = MainActivity.class.getSimpleName();

    private GLSurfaceView glView;
    private SimpleRenderer renderer;

    private Cube cube;
    private Pyramid pyramid;

    private double systemtime1;
    private DecimalFormat decimalFormat1;
    private Timer timer1;
    private int countX;
    private int countY;
    private int countZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        timer1 = new Timer();
        decimalFormat1 = new DecimalFormat("0.000");
        countX=0;
        countY=0;
        countZ=0;

        glView = findViewById(R.id.gl_view);
        SeekBar seekBarX = findViewById(R.id.seekbar_x);
        SeekBar seekBarY = findViewById(R.id.seekbar_y);
        SeekBar seekBarZ = findViewById(R.id.seekbar_z);
        seekBarX.setMax(360);
        seekBarY.setMax(360);
        seekBarZ.setMax(360);
        seekBarX.setOnSeekBarChangeListener(this);
        seekBarY.setOnSeekBarChangeListener(this);
        seekBarZ.setOnSeekBarChangeListener(this);

        //Auto-rotate via SeekBar
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                systemtime1 = System.currentTimeMillis();

                SeekBar seekBarX = findViewById(R.id.seekbar_x);
                SeekBar seekBarY = findViewById(R.id.seekbar_y);
                SeekBar seekBarZ = findViewById(R.id.seekbar_z);
                seekBarX.setProgress(countX);
                seekBarY.setProgress(countY);
                seekBarZ.setProgress(countZ);

                countX += 1;
                countY += 2;
                countZ += 3;
                if (countX==360){countX=0;}
                if (countY==360){countY=0;}
                if (countZ==360){countZ=0;}
            }
        }, 50, 50);


        renderer = new SimpleRenderer();
        cube = new Cube();
        pyramid = new Pyramid();
        renderer.setObj(cube);
        glView.setRenderer(renderer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        glView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        glView.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
        case R.id.menu_cube:
            renderer.setObj(cube);
            break;
        case R.id.menu_pyramid:
            renderer.setObj(pyramid);
            break;
        }
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
        case R.id.seekbar_x:
            renderer.rotateObjX(progress);
            break;
        case R.id.seekbar_y:
            renderer.rotateObjY(progress);
            break;
        case R.id.seekbar_z:
            renderer.rotateObjZ(progress);
            break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
