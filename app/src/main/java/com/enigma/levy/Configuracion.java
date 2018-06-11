package com.enigma.levy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.Random;

import Datos.BackendConnection;
import Datos.Usuario;

public class Configuracion extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    RadioButton radioButton;
    RadioButton radioButton2;
    RadioButton radioButton3;
    EditText editText;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    String path="levy";
    String pathDir="https://s3.us-east-2.amazonaws.com/mylevy-userfiles-mobilehub-976529463/images/";
    final String validChars = "abcdefghijklmnopqrstuvwxyz";
    Random rnd = new Random();

    public void radioClick(View view){

        if(view.getTag().equals("lista")){
            radioButton.setChecked(true);
            radioButton2.setChecked(false);
            radioButton3.setChecked(false);
        }
        else if(view.getTag().equals("carta")){
            radioButton.setChecked(false);
            radioButton2.setChecked(false);
            radioButton3.setChecked(true);
        }
        else{
            radioButton.setChecked(false);
            radioButton2.setChecked(true);
            radioButton3.setChecked(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));

        int postion = getIntent().getIntExtra("position", 0);
        Resources resources = getResources();

        collapsingToolbar.setTitle(MainActivity.user.getNombre()+ " " + MainActivity.user.getApellido1()+" "+MainActivity.user.getApellido2());
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.primaryColor));

        ImageView placePicutre = (ImageView) findViewById(R.id.image);
        Glide.with(Configuracion.this).load(MainActivity.user.getImagen()).into(placePicutre);

        sharedPreferences= this.getSharedPreferences("com.enigma.levy", getApplicationContext().MODE_PRIVATE);
        radioButton = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        String share= sharedPreferences.getString("list","lista");
        if(share.equals("carta")){
            radioButton.setChecked(false);
            radioButton3.setChecked(true);
        }
        else if(share.equals("azulejo")){
            radioButton.setChecked(false);
            radioButton2.setChecked(true);
        }

        editText =  findViewById(R.id.editText2);
        editText2 = findViewById(R.id.editText6);
        editText3 = findViewById(R.id.editText7);
        editText4 = findViewById(R.id.editText8);
        editText.setText(MainActivity.user.getNombre());
        editText2.setText(MainActivity.user.getApellido1());
        editText4.setText(MainActivity.user.getApellido2());
        editText3.setText(MainActivity.user.getContrasenna());
    }

    public void saveChanges(View view){
        if(radioButton.isChecked()){
            sharedPreferences.edit().putString("list","lista").apply();
        }
        else if (radioButton2.isChecked()){
            sharedPreferences.edit().putString("list","azulejo").apply();
        }
        else{
            sharedPreferences.edit().putString("list","carta").apply();
        }
        if(!path.equals("levy")){
            String imagen= MainActivity.user.getImagen();
            imagen = imagen.substring(imagen.indexOf("images/")+7,imagen.length()-4);
            if(imagen.equals("levy")){
                imagen = 2+randomString(10)+1;
            }
            else{
                Log.d("Imagen",imagen);
                int i= Integer.parseInt(imagen.substring(imagen.length()-1));
                i++;
                imagen = imagen.substring(0,imagen.length()-1)+i;
                Log.d("Imagen",imagen);
            }
            uploadWithTransferUtility( path, imagen+path.substring(path.length()-4));
            MainActivity.user.setImagen(pathDir+imagen+path.substring(path.length()-4));
            Log.d("IMAGEN", imagen);
        }
        if(!editText.getText().toString().equals(MainActivity.user.getNombre()) ||
                !editText2.getText().toString().equals(MainActivity.user.getApellido1()) ||
                !editText4.getText().toString().equals(MainActivity.user.getApellido2()) ||
                !editText3.getText().toString().equals(MainActivity.user.getContrasenna()) ||
                !path.equals("levy")
                ){
            Usuario usuario= BackendConnection.ActualizaUsuario(MainActivity.user.getId(),editText.getText().toString(),editText2.getText().toString(),
                    editText4.getText().toString(),MainActivity.user.getImagen(),MainActivity.user.getCorreo(),editText3.getText().toString(),MainActivity.user.getToken());
            if(usuario!=null){
                MainActivity.user=usuario;
                sharedPreferences.edit().putString("token",usuario.getToken()).apply();
            }
            else{
                Toast.makeText(Configuracion.this,"Error al modificar los datos",Toast.LENGTH_SHORT).show();
            }

        }
        Intent intent = new Intent(Configuracion.this,Principal.class);
        startActivity(intent);

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
                if (resultCode == Configuracion.RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    String selectedPath = selectedImage.getPath();
                    if (requestCode == 1) {

                        if (selectedPath != null) {
                            ImageView imageView= findViewById(R.id.image);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    public String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( validChars.charAt( rnd.nextInt(validChars.length()) ) );
        return sb.toString();
    }
}
