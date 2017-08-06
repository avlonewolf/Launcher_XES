package backbenchersbeta.xeslauncher;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {
    private ImageButton i1,i2,i3,i4,i5;
    private TextView mTextView;
    private RelativeLayout r;
    String finalres;
    GestureDetectorCompat g;
    int count=0,cc=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_main);

        if (isNetworkAvailable()) {
            new TestMain().execute();
            }
        i1 = (ImageButton) findViewById(R.id.imageButton1);
        i2 = (ImageButton) findViewById(R.id.imageButton2);
        i3 = (ImageButton) findViewById(R.id.imageButton3);
        i4 = (ImageButton) findViewById(R.id.imageButton4);
        i5 = (ImageButton) findViewById(R.id.imageButton5);
        r=(RelativeLayout)findViewById(R.id.rel);
        g = new GestureDetectorCompat(this,new MyGestureListener());

        r.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return g.onTouchEvent(event);
            }
        });
        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                i1 = (ImageButton) findViewById(R.id.imageButton1);
                i2 = (ImageButton) findViewById(R.id.imageButton2);
                i3 = (ImageButton) findViewById(R.id.imageButton3);
                i5 = (ImageButton) findViewById(R.id.imageButton5);
                Animation appear = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.appear1);
                Animation appear2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.appear2);
                Animation appear3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.appear3);
                Animation appear4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.appear4);
                Animation vanish = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.vanish);
                Animation anti = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anti);
                Animation cloc = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloc);
                if (cc % 2 != 0) {
                    i1.startAnimation(vanish);
                    i2.startAnimation(vanish);
                    i3.startAnimation(vanish);
                    i5.startAnimation(vanish);
                    i4.startAnimation(anti);
                    i1.setAlpha((float) 0.0);
                    i2.setAlpha((float) 0.0);
                    i3.setAlpha((float) 0.0);
                    i5.setAlpha((float) 0.0);

                    cc++;
                } else {
                    i1.startAnimation(appear2);
                    i5.startAnimation(appear3);
                    i2.startAnimation(appear4);
                    i3.startAnimation(appear);
                    i4.startAnimation(cloc);
                    i1.setAlpha((float) 1.0);
                    i2.setAlpha((float) 1.0);
                    i3.setAlpha((float) 1.0);
                    i3.setAlpha((float) 1.0);
                    i5.setAlpha((float) 1.0);
                    i1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent i = getPackageManager().getLaunchIntentForPackage("talker2.xes.com.talker2");
                            i.setAction(Intent.ACTION_SEND);
                            i.putExtra(Intent.EXTRA_TEXT, finalres);
                            i.setType("text/plain");
                            startActivity(i);

                        }
                    });
                    i2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent i = getPackageManager().getLaunchIntentForPackage("login.xes.com.login");
                            startActivity(i);

                        }
                    });
                    i3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = getPackageManager().getLaunchIntentForPackage("com.xes.minewps");
                            startActivity(i);
                        }
                    });
                    i5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = getPackageManager().getLaunchIntentForPackage("com.example.pubnubheartrate");
                            startActivity(i);

                        }
                    });
                    cc++;
                }
            }
        });
        i4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                count = 0;
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                return false;
            }
        });
    } public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public class TestMain extends AsyncTask<String, Void, String>
    {

        OkHttpClient client;

        @Override
        protected void onPreExecute() {

            client = new OkHttpClient();
            finalres="";
        }

        @Override
        protected String doInBackground(String... params)
        {
            Request request = new Request.Builder().url("http://gxconnect.16mb.com/ankit/?key=hadroncollid3r&method=getentry").build();

            Response response = null;
            try
            {
                response = client.newCall(request).execute();
                finalres= response.body().string();

            } catch (IOException e) { e.printStackTrace();}

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            finalres="{\"error\":0,\"response\":[{\"id\":\"1\",\"tid\":1,\"Fname\":\"Anshuman\",\"ip\":\"172.16.59.8\",\"Designation\":\"Supervisor\"},{\"id\":\"2\",\"tid\":1,\"Fname\":\"Dutta\",\"ip\":\"172.16.59.73\",\"Designation\":\"Worker\"},{\"id\":\"3\",\"tid\":1,\"Fname\":\"Vijeet\",\"ip\":\"192.168.43.120\",\"Designation\":\"Engineer\"},{\"id\":\"4\",\"tid\":1,\"Fname\":\"BaseStation\",\"ip\":\"172.16.59.0\",\"Designation\":\"\"}]}";

            //dostuff(finalres);

        }
    }

    @Override
    public void onBackPressed(){
        count++;
        if(count>4){
            this.finish();
        }
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

                Intent i = new Intent (MainActivity.this, TalkerActivity.class);
                startActivity(i);
            }
            else if (x>0&&Math.abs(x)>50&&Math.abs(x)>Math.abs(y)) {
                //swipe right

                Intent i = new Intent(MainActivity.this, TrackerActivity.class);
                startActivity(i);
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
