package in.calibrage.teluguchurches.views.Activities;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import in.calibrage.teluguchurches.MainActivity;
import in.calibrage.teluguchurches.R;
import in.calibrage.teluguchurches.util.CircleTransform;

public class ActivityProfile extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout = null;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        ImageView imgprofile = findViewById(R.id.img_profile);
        collapsingToolbarLayout = findViewById(R.id.lyt_collapsing);
        Picasso.with(this).load("https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAAbkAAAAJDRlNDk1ODcwLTAwZTEtNDIzMy1hYTIyLTIxODkxZDJlZWQ0OQ.jpg").transform(new CircleTransform()).into(imgprofile);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profileimg);
        Bitmap blurredBitmap = blur(bitmap);
        BitmapDrawable background = new BitmapDrawable(blurredBitmap);
        collapsingToolbarLayout.setBackgroundDrawable(background);




    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap blur(Bitmap image) {
        if (null == image) return null;
        Bitmap outputBitmap = Bitmap.createBitmap(image);
        final RenderScript renderScript = RenderScript.create(this);
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);
        //Intrinsic Gausian blur filter
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(25);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }

}
