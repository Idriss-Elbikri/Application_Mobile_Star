package com.example.starmobile_application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starmobile_application.adapter.StarAdapter;
import com.example.starmobile_application.beans.Star;
import com.example.starmobile_application.service.StarService;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private static final String TAG = "ListActivity";
    private List<Star> stars;
    private RecyclerView recyclerView;
    private StarAdapter starAdapter = null;
    private StarService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Vérifiez ces lignes pour vous assurer qu'elles ne causent pas d'erreur
        stars = new ArrayList<>();
        service = StarService.getInstance();
        init();  // Le problème pourrait être ici, dans les ressources référencées

        recyclerView = findViewById(R.id.recycle_view);
        // Assurez-vous que recyclerView n'est pas null
        if (recyclerView != null) {
            starAdapter = new StarAdapter(this, service.findAll());
            recyclerView.setAdapter(starAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

//    public void init() {
//        // Utilisation des images locales du dossier drawable
//        service.create(new Star("Kate Bosworth", "android.resource://" + getPackageName() + "/" + R.drawable.kate_bosworth, 3.5f));
//        service.create(new Star("George Clooney", "android.resource://" + getPackageName() + "/" + R.drawable.george_clooney, 3.0f));
//        service.create(new Star("Michelle Rodriguez", "android.resource://" + getPackageName() + "/" + R.drawable.michelle_rodriguez, 5.0f));
//        service.create(new Star("Leonardo DiCaprio", "android.resource://" + getPackageName() + "/" + R.drawable.leonardo_dicaprio, 4.5f));
//        service.create(new Star("Scarlett Johansson", "android.resource://" + getPackageName() + "/" + R.drawable.scarlett_johansson, 4.0f));
//        service.create(new Star("Tom Hanks", "android.resource://" + getPackageName() + "/" + R.drawable.tom_hanks, 5.0f));
//        service.create(new Star("Meryl Streep", "android.resource://" + getPackageName() + "/" + R.drawable.meryl_streep, 4.8f));
//        service.create(new Star("Denzel Washington", "android.resource://" + getPackageName() + "/" + R.drawable.denzel_washington, 4.2f));
//        service.create(new Star("Natalie Portman", "android.resource://" + getPackageName() + "/" + R.drawable.natalie_portman, 4.3f));
//        service.create(new Star("Robert Downey Jr.", "android.resource://" + getPackageName() + "/" + R.drawable.robert_downey_jr, 4.7f));
//        service.create(new Star("Jennifer Lawrence", "android.resource://" + getPackageName() + "/" + R.drawable.jennifer_lawrence, 4.1f));
//        service.create(new Star("Brad Pitt", "android.resource://" + getPackageName() + "/" + R.drawable.brad_pitt, 4.0f));
//        service.create(new Star("Emma Stone", "android.resource://" + getPackageName() + "/" + R.drawable.emma_stone, 4.4f));
//        service.create(new Star("Chris Hemsworth", "android.resource://" + getPackageName() + "/" + R.drawable.chris_hemsworth, 4.2f));
//        service.create(new Star("Angelina Jolie", "android.resource://" + getPackageName() + "/" + R.drawable.angelina_jolie, 3.9f));
//        service.create(new Star("Will Smith", "android.resource://" + getPackageName() + "/" + R.drawable.will_smith, 4.1f));
//        service.create(new Star("Charlize Theron", "android.resource://" + getPackageName() + "/" + R.drawable.charlize_theron, 4.3f));
//    }

    public void init(){
        service.create(new Star("Aziz Da", "https://media0070.elcinema.com/uploads/_640x_a090742e5f9953b53b53ad7a6370baa853690804b88aaf362f08e837e7881d2c.jpg", 3.5f));
        service.create(new Star("Rafik Bo", "https://upload.wikimedia.org/wikipedia/commons/d/db/Rafik_Boubker.jpg", 3));
        service.create(new Star("Majdouline","https://media0070.elcinema.com/uploads/_640x_d34d1a55a45bc2d066362c17d18b114f1f4dea8a31439bbb6566f324dbc96cd4.jpg", 5));
        service.create(new Star("Said Mo", "https://cdn-images.dzcdn.net/images/artist/817dee73cd56641be2ba26ae4456a4c2/500x500-000000-80-0-0.jpg", 1));
        service.create(new Star("ibtisam Ti", "https://upload.wikimedia.org/wikipedia/commons/0/0a/%D8%A7%D8%A8%D8%AA%D8%B3%D8%A7%D9%85_%D8%AA%D8%B3%D9%83%D8%AA.jpg", 5));
        service.create(new Star("Salma Ra", "https://lastfm.freetls.fastly.net/i/u/ar0/9d329c38e2fb11e5b2d08d27d9d3591a.jpg", 1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        // Configuration de la recherche
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("Nom de star...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (starAdapter != null) {
                    starAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.share) {
            // Créer un intent de partage avec un message descriptif
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Stars");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Découvrez ma galerie de stars de cinéma ! Une application géniale pour noter vos acteurs préférés.");

            // Ouvrir le sélecteur de partage (WhatsApp, Bluetooth, etc.)
            startActivity(Intent.createChooser(shareIntent, "Partager via"));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
