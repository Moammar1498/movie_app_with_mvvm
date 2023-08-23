package online.diligence.movieslist.network;

import online.diligence.movieslist.utils.credentials;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceImpl {

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(credentials.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();


    private static ApiService apiService =retrofit.create(ApiService.class);

    public static ApiService getMovieApi(){
        return apiService;
    }

}
