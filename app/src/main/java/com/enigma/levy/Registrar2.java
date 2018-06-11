package com.enigma.levy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import Datos.Amazon;
import Datos.BackendConnection;
import Datos.DataParserJ;
import Datos.Usuario;

public class Registrar2 extends AppCompatActivity {
    String path="levy";
    String name="";
    String pathDir="https://s3.us-east-2.amazonaws.com/mylevy-userfiles-mobilehub-976529463/images/";
    final String validChars = "abcdefghijklmnopqrstuvwxyz";
    Random rnd = new Random();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar2);
        AWSMobileClient.getInstance().initialize(this).execute();
        name= 2+randomString(10)+1;
        sharedPreferences= this.getSharedPreferences("com.enigma.levy", getApplicationContext().MODE_PRIVATE);
    }

    public void abrirGaleria(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(
                Intent.createChooser(intent, "Seleccione una imagen"),
                1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Uri selectedImage;
        switch (requestCode) {
            case 1:
                if (resultCode == Registrar2.RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    String selectedPath = selectedImage.getPath();
                    if (requestCode == 1) {

                        if (selectedPath != null) {
                            ImageView imageView= findViewById(R.id.imageView3);
                            imageView.setImageURI(selectedImage);
                            String[] projection = { MediaStore.Images.Media.DATA };

                            Cursor cursor = getContentResolver().query(selectedImage, projection, null, null, null);
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(projection[0]);
                            String picturePath = cursor.getString(columnIndex); // returns null
                            cursor.close();
                            if(picturePath!=null){
                                Log.d("PATH", picturePath);
                                path=picturePath;
                            }
                        }
                    }
                }
                break;
        }


    }

    public void signup(View view){
        String image="";
        if(path.equals("levy")){
            image=pathDir+"levy.png";
        }
        else{
            Log.d("PATY",path);
            Log.d("PATY",name+path.substring(path.length()-4));
            uploadWithTransferUtility( path, name+path.substring(path.length()-4));
            image=pathDir+name+path.substring(path.length()-4);
        }
        final Usuario usuario= BackendConnection.Registrar(Registrar1.nombre,Registrar1.apellido1,Registrar1.apellido2
                                    ,Registrar1.correo,Registrar1.contrasenna
                                    ,image);
        if(usuario!=null){
            Log.d("IDENTIFIER",usuario.getId());
            new AlertDialog.Builder(this).setIcon(R.drawable.logo)
                    .setTitle("Ya estás logueado")
                    .setMessage("Quiere empezar la experiencia Findoor?")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Registrar1.contrasenna="";
                            Registrar1.nombre="";
                            Registrar1.correo="";
                            Registrar1.apellido1="";
                            Registrar1.apellido2="";
                            sharedPreferences.edit().putString("token",usuario.getToken()).apply();
                            Intent intent = new Intent(Registrar2.this,MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Registrar1.contrasenna="";
                            Registrar1.nombre="";
                            Registrar1.correo="";
                            Registrar1.apellido1="";
                            Registrar1.apellido2="";
                            Intent intent = new Intent(Registrar2.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }).show();
        }
        else{
            Toast.makeText(Registrar2.this,"Error a la hora de registrar",Toast.LENGTH_SHORT).show();
        }
    }

    public String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( validChars.charAt( rnd.nextInt(validChars.length()) ) );
        return sb.toString();
    }

    public void uploadWithTransferUtility(String path, String name) {

        // KEY and SECRET are gotten when we create an IAM user above
        BasicAWSCredentials credentials = new BasicAWSCredentials("AKIAJCGEXRAMTFFKBLYA", "Lt0741Z8AEAjwaIyqkGUuanwCjj5TEqFbCS6bxF9");
        AmazonS3Client s3Client = new AmazonS3Client(credentials);

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(s3Client)
                        .build();
        TransferObserver uploadObserver =
                transferUtility.upload("images/" + name, new File(path));

        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed download.
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                int percentDone = (int)percentDonef;
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
            }

        });

// If your upload does not trigger the onStateChanged method inside your
// TransferListener, you can directly check the transfer state as shown here.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            // Handle a completed upload.
        }
    }


}
