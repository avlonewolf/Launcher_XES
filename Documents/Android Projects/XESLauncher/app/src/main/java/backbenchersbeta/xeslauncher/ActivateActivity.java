package backbenchersbeta.xeslauncher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Vijeet on 3/20/2016.
 */
public class ActivateActivity extends Activity {

    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activate_screen);

        b1 = (Button) findViewById(R.id.activatebutton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivateActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }


}
