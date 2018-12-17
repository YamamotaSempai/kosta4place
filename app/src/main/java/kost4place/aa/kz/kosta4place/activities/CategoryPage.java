package kost4place.aa.kz.kosta4place.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import kost4place.aa.kz.kosta4place.adapter.PostAdapter;
import kost4place.aa.kz.kosta4place.model.Place;
import kost4place.aa.kz.kosta4place.model.PlaceWithCategory;
import kost4place.aa.kz.kosta4place.repository.PlaceRepository;

public class CategoryPage extends AppCompatActivity {

    private static String category;
    private static final String TAG = "CategoryPage";
    private RecyclerView recyclerView_posts;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private PlaceRepository placeRepository = new PlaceRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_category);

        getIncomingIntent();

        //View
        recyclerView_posts = findViewById(R.id.recycler_posts);
        recyclerView_posts.setHasFixedSize(true);
        recyclerView_posts.setLayoutManager(new LinearLayoutManager((this)));

        fetchData();
    }

    //Error when the activity try to get a information without API
    private void fetchData() {
        compositeDisposable.add(placeRepository.getPlaces(getApplicationContext(), category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(this::displayData)
                .subscribe());
    }

    private void displayData(List<PlaceWithCategory> placeWithCategories) {
        for (PlaceWithCategory placeWithCategory : placeWithCategories) {
            PostAdapter adapter = new PostAdapter(this, placeWithCategory.places);
            recyclerView_posts.setAdapter(adapter);
        }
    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if (getIntent().hasExtra("category")) {
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            category = getIntent().getStringExtra("category");
        }
    }

    @Override
    protected void onStop() {
        if (compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
