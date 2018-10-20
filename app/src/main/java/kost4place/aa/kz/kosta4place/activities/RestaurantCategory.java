package kost4place.aa.kz.kosta4place.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.reactivestreams.Subscriber;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import kost4place.aa.kz.kosta4place.R;
import kost4place.aa.kz.kosta4place.adapter.PostAdapter;
import kost4place.aa.kz.kosta4place.api.KostaBasicApi;
import kost4place.aa.kz.kosta4place.dao.DatabaseCallback;
import kost4place.aa.kz.kosta4place.model.Place;
import kost4place.aa.kz.kosta4place.service.KostaServiceRetrofit;
import retrofit2.Retrofit;

public class RestaurantCategory extends AppCompatActivity implements DatabaseCallback {

    private KostaBasicApi kostaBasicApi;
    private RecyclerView recyclerView_posts;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_category);

        //Init
        Retrofit retrofit = KostaServiceRetrofit.getInstance();
        kostaBasicApi = retrofit.create(KostaBasicApi.class);

        //View
        recyclerView_posts = findViewById(R.id.recycler_posts);
        recyclerView_posts.setHasFixedSize(true);
        recyclerView_posts.setLayoutManager(new LinearLayoutManager((this)));

        fetchData();
    }

    private void fetchData() {
        compositeDisposable.add(kostaBasicApi.getPlace()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayData));
    }

    private void displayData(List<Place> places) {
        PostAdapter adapter = new PostAdapter(this, places);
        recyclerView_posts.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    public void onPlacesLoaded(List<Place> users) {

    }

    @Override
    public void onDataNotAvailable() { // don't need

    }

    @Override
    public void onPlaceAdded() { // don't need

    }
}
