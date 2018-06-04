package com.enigma.levy;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;

public class Ingresar2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar2);
    }

    public void abrirGaleria(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Seleccione una imagen"),
                1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Uri selectedImage;
        switch (requestCode) {
            case 1:
                if (resultCode == Ingresar2.RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    String selectedPath = selectedImage.getPath();
                    if (requestCode == 1) {

                        if (selectedPath != null) {
                            InputStream imageStream = null;
                            /*try {
                                imageStream = getContentResolver().openInputStream(
                                        selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            Bitmap bmp= BitmapFactory.decodeStream(imageStream);*/

                            ImageView imageView= findViewById(R.id.imageView3);
                            //imageView.setImageBitmap(bmp);
                            imageView.setImageURI(selectedImage);
                        }
                    }
                }
                break;
        }


    }
}
