package fr.uha.contact;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.Serializable;

public class ContactActivity extends AppCompatActivity {


    private static final int PERMISSIONS_TO_CALL = 911;
    private static final int DIALOG_ALERT = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactdet_activity);


        Contact perso = (Contact) getIntent().getSerializableExtra("CONTACT");
        EditText nom = findViewById(R.id.editTextTextPersonName);
        EditText prenom = findViewById(R.id.editTextTextPersonPrenom);
        EditText tel = findViewById(R.id.editTextTextPersonTel);
        if (perso != null) {
            nom.setText(perso.getNOM());
            prenom.setText(perso.getPRENOM());
            tel.setText(perso.getNUM());
        }
        //APP icon

        ActionBar menu = getSupportActionBar();// Recupère les props du menu
        menu.setDisplayHomeAsUpEnabled(true);// Affiche le app icon
        menu.setDisplayShowTitleEnabled(false);// Affiche pas le titre
        menu.setHomeAsUpIndicator(R.mipmap.ic_launcher);// Choix du logo




    }

    public void buttquitt(View v) {

        setResult(RESULT_CANCELED);
        finish();
    }

    public void buttvalide(View v) {

        EditText Casenom = findViewById(R.id.editTextTextPersonName);
        EditText Caseprenom = findViewById(R.id.editTextTextPersonPrenom);
        EditText Casetel = findViewById(R.id.editTextTextPersonTel);

        String nom = Casenom.getText().toString();
        String prenom = Caseprenom.getText().toString();
        String tel = Casetel.getText().toString();

        Contact perso3 = new Contact(nom, prenom, tel);
        int position = getIntent().getIntExtra("POSITION", -1);


        Intent data = new Intent();
        data.putExtra("CONTACT", perso3);
        data.putExtra("POSITION", position);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return true;
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_TO_CALL: {
// If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
// Permission(s) acceptée(s) à appel téléphonique
                } else {
// Permission(s) refusée(s) par l’utilisateur

                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.del:
                showDialog(DIALOG_ALERT);

                return true;
            case R.id.app:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_TO_CALL);
                } else {
                    EditText et = findViewById(R.id.editTextTextPersonTel);
                    String tel = et.getText().toString();
                    Intent appel = new Intent(Intent.ACTION_CALL);
                    appel.setData(Uri.parse("tel: " + tel));
                    startActivity(appel);
                    return true;
                }
            /*case R.id.annul:
                setResult(RESULT_CANCELED);
                finish();*/
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;
        }




        return super.onOptionsItemSelected(item);
    }


    //////////// DIALOGUE DE SUPP


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ALERT:
                // Create out AlterDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                Contact perso = (Contact) getIntent().getSerializableExtra("CONTACT");
                String pre = perso.getPRENOM() + " " + perso.getNOM();
                builder.setMessage("Etes-vous sûr de vouloir supprimer "+ pre);
                builder.setCancelable(true);
                builder.setPositiveButton("Bien sûr !", new OkOnClickListener());
                builder.setNegativeButton("Oula NON!", new CancelOnClickListener());
                AlertDialog dialog = builder.create();
                dialog.show();
        }
        return super.onCreateDialog(id);
    }

    private final class CancelOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getApplicationContext(), "Activity will continue",
                    Toast.LENGTH_LONG).show();
        }
    }

    private final class OkOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Intent data2 = new Intent();
            int position = getIntent().getIntExtra("POSITION",-1);
            data2.putExtra("POSITION",position);
            setResult(RESULT_FIRST_USER,data2);
            finish();

        }
    }

}









