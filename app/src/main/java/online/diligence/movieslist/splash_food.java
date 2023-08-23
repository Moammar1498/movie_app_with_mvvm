package online.diligence.movieslist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class splash_food extends AppCompatActivity {

    ConstraintLayout splash;
    TextView Fresh, hello, header, desc;
    Button try_btn;
    ImageView main_img, header_arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_food);

        splash = findViewById(R.id.splash);
        Fresh = findViewById(R.id.fresh);
        hello = findViewById(R.id.hello);
        header = findViewById(R.id.header_text);
        desc = findViewById(R.id.desc);

        main_img = findViewById(R.id.center_image);
        header_arrow = findViewById(R.id.round_arrow);


        int[] bgClrs = new int[]{
                Color.parseColor("#71AE9E"),
                Color.parseColor("#5FDD99"),
                Color.parseColor("#388D77"),
                Color.parseColor("#27715D"),
        };
        final GradientDrawable bgGrad = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,bgClrs);
        bgGrad.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        bgGrad.setBounds(0,0,splash.getMaxWidth(),splash.getMaxHeight());
        splash.setBackground(bgGrad);
        TextPaint paint = Fresh.getPaint();
        float width = paint.measureText("Fresh");
        Shader textShader = new LinearGradient(0, 0, width, Fresh.getTextSize(),
                new int[]{
                        Color.parseColor("#F28128"),
                        Color.parseColor("#FF4C31"),
                }, null, Shader.TileMode.CLAMP);
        Fresh.getPaint().setShader(textShader);

        try_btn = findViewById(R.id.try_btn);

        try_btn.setOnClickListener(view -> {
            Intent intent = new Intent(splash_food.this,MovieListActivity.class );
            startActivity(intent);
        });
    }
}