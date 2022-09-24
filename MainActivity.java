package fr.uha.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.Collections;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ContactAdapter adapter = new ContactAdapter(this);

    private static final int CODE_REQUETE = 101;
    private static final int CODE_NOUVEAU = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter.ajout();
        Collections.sort(adapter.liste,Contact::compareTo);


        ListView liste2 = findViewById(R.id.ListView);
        liste2.setAdapter(adapter);

        liste2.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Contact perso = adapter.get(position);
        Intent intent1 = new Intent(this, ContactActivity.class);

        intent1.putExtra("CONTACT", perso);
        intent1.putExtra("POSITION", position);


        startActivityForResult(intent1, CODE_REQUETE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_REQUETE) {

            if (resultCode == RESULT_OK) {

                Contact perso2 = (Contact) data.getSerializableExtra("CONTACT");
                int position = data.getIntExtra("POSITION", -1);
                adapter.set(position, perso2);
                //Toast.makeText(this,perso2.getNOM()+" est modifié",Toast.LENGTH_LONG).show();
                Collections.sort(adapter.liste,Contact::compareTo);
                adapter.notifyDataSetChanged();


            } else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(this, "Opération annulée",
                        Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_FIRST_USER) {

                int position = (int) data.getIntExtra("POSITION", -1);
                adapter.remove(position);
                adapter.notifyDataSetChanged();

            }


        }
        if (requestCode == CODE_NOUVEAU) {
            if (resultCode == RESULT_OK) {
                Contact perso4 = (Contact) data.getSerializableExtra("CONTACT");
                adapter.add(perso4);
                Collections.sort(adapter.liste,Contact::compareTo);
                adapter.notifyDataSetChanged();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Opération annulée",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {

            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        };



            searchView.setOnQueryTextListener(queryTextListener);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.ajout:
                Intent ajout = new Intent(this, ContactActivity.class);
                startActivityForResult(ajout, CODE_NOUVEAU);

                return true;

            case R.id.annul:
                finish();
                return true;
            case R.id.app_bar_search:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



}






