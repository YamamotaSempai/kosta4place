package kost4place.aa.kz.kosta4place.remote.api;

import java.util.List;

import io.reactivex.Observable;
import kost4place.aa.kz.kosta4place.remote.model.RemotePlace;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface KostaApi {
    @GET("all")
    Observable<List<RemotePlace>> getAllPlaces();

    @GET("/category/{category}")
    Observable<List<RemotePlace>> getPlacesByCategory(@Path("category") String category);

    @GET("/place/{id}")
    Observable<RemotePlace> getPlaceById(@Path("id") Long id);

}
