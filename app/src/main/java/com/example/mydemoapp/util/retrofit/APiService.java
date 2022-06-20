package com.example.mydemoapp.util.retrofit;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APiService {
    @GET("/api/unknown")
    Observable<MyModel> doGetListResources();

}
