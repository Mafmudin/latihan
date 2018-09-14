package udinsi.dev.bookcollection.uiux;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import udinsi.dev.bookcollection.R;

public class Ui extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);

        final ImageView imageView = findViewById(R.id.imageUser);
        final TextView name = findViewById(R.id.name);
        final TextView desc = findViewById(R.id.desc);

        imageView.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ui.this, Ux.class);

                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair<View, String>(imageView, "imageTransition");
                pairs[1] = new Pair<View, String>(name, "nameTransition");
                pairs[2] = new Pair<View, String>(desc, "descTransition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Ui.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
    }

}
