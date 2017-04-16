package debrie.android.fr.spibens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        final Button b = (Button) findViewById(R.id.updateProfile);
        final EditText t1 = (EditText) findViewById(R.id.editText1);
        final EditText t2 = (EditText) findViewById(R.id.editText2);
        final EditText t3 = (EditText) findViewById(R.id.editText3);
        final EditText t4 = (EditText) findViewById(R.id.editText4);
        final EditText t5 = (EditText) findViewById(R.id.editText5);
        final EditText t6 = (EditText) findViewById(R.id.editText6);

        t1.setEnabled(false);
        t2.setEnabled(false);
        t3.setEnabled(false);
        t4.setEnabled(false);
        t5.setEnabled(false);
        t6.setEnabled(false);

        b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        t1.setEnabled(!t1.isEnabled());
                        t2.setEnabled(!t2.isEnabled());
                        t3.setEnabled(!t3.isEnabled());
                        t4.setEnabled(!t4.isEnabled());
                        t5.setEnabled(!t5.isEnabled());
                        t6.setEnabled(!t6.isEnabled());
                    }
                });
    }
}
