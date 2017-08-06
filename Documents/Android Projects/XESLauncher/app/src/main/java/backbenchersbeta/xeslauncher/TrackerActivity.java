package backbenchersbeta.xeslauncher;

/**
 * Created by Vijeet on 3/8/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class TrackerActivity extends Activity {

    ImageButton i1;
    RelativeLayout r1;
    int count;
    GestureDetectorCompat g;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracker_launcher);
        count=0;
        i1 = (ImageButton) findViewById(R.id.imageButton1);
        r1 = (RelativeLayout) findViewById(R.id.reltrack);
        g= new GestureDetectorCompat(this, new MyGestureListener());
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getPackageManager().getLaunchIntentForPackage("com.xes.minewps");
                startActivity(i);
            }
        });
        r1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return g.onTouchEvent(event);
            }
        });
        /*i1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return g.onTouchEvent(event);
            }
        });*/
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus){
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float VelocityX, float velocityY)
        {	float x = (e2.getX()-e1.getX());
            float y = (e2.getY()-e1.getY());
            if (x<0&&Math.abs(x)>50&&Math.abs(x)>Math.abs(y))
            {
                //swipe left
                finish();
            }
            return true;
        }
        @Override
        public boolean onDown(MotionEvent e){
            return true;
        }
        @Override
        public boolean onSingleTapUp(MotionEvent e){
            return true;
        }
    }
}
