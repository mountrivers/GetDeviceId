package com.sanha.getdeviceid;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    EditText TX ;
    Button bt,bt2;
    public  String android_id;
    ClipboardManager clipboardManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TX = (EditText) findViewById(R.id.tx);

        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        IDManger.BigSetBannerAd(this,(FrameLayout)findViewById(R.id.m_adview));
        TX.setText(md5(android_id).toUpperCase());
        bt = (Button) findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent msg = new Intent(Intent.ACTION_SEND);

                msg.addCategory(Intent.CATEGORY_DEFAULT);

                msg.putExtra(Intent.EXTRA_SUBJECT, "Device ID");

                msg.putExtra(Intent.EXTRA_TEXT, md5(android_id).toUpperCase());

                msg.putExtra(Intent.EXTRA_TITLE, "Device ID");

                msg.setType("text/plain");

                startActivity(Intent.createChooser(msg, "공유하기"));

            }
        });
        bt2 = (Button) findViewById(R.id.button2);
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clip = ClipData.newPlainText("clip", TX.getText().toString());
                clipboardManager.setPrimaryClip(clip);
            }
        });
    }
    public static String md5(String s) {
        try {

            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();


            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
