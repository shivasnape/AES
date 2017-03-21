package com.snape.shivichu.symmetricencryptionaes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    TextView tOriginal,tEncode,tDecode;
    static final String TAG = "SymmetricAlgorithm-AES";
    String theTestText = "Friendship is a Valuable Gift";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tOriginal=(TextView)findViewById(R.id.txt_original);
        tEncode=(TextView)findViewById(R.id.txt_encoded);
        tDecode=(TextView)findViewById(R.id.txt_decoded);

        tOriginal.setText(theTestText);

        // Set up secret key spec for 128-bit AES encryption and decryption
        SecretKeySpec secretKeySpec = null;
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed("any data used as random seed".getBytes());
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128, secureRandom);
            secretKeySpec = new SecretKeySpec((keyGenerator.generateKey()).getEncoded(), "AES");
        } catch (Exception e) {
            Log.e(TAG, "AES secret key spec error");
        }



        // Encode the original data with AES
        byte[] encodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            encodedBytes = c.doFinal(theTestText.getBytes());
        } catch (Exception e) {
            Log.e(TAG, "AES encryption error");
        }

        //setting encoded String in to Encoded TextView
        tEncode.setText(Base64.encodeToString(encodedBytes, Base64.DEFAULT) );


        // Decode the encoded data with AES
        byte[] decodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, secretKeySpec);
            decodedBytes = c.doFinal(encodedBytes);
        } catch (Exception e) {
            Log.e(TAG, "AES decryption error");
        }

        //setting Decoded String in to TextView
        tDecode.setText(new String(decodedBytes));


    }
}
