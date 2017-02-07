package info.blockchain.util;

import info.blockchain.wallet.api.PersistentUrls;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RestClient {

    public static Retrofit getRetrofitApiInstance(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(PersistentUrls.getInstance().getCurrentBaseApiUrl())
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
