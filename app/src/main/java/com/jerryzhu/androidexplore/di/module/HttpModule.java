package com.jerryzhu.androidexplore.di.module;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.jerryzhu.androidexplore.BuildConfig;
import com.jerryzhu.androidexplore.app.AndroidExploreApp;
import com.jerryzhu.androidexplore.app.Constants;
import com.jerryzhu.androidexplore.core.http.exception.GeekApis;
import com.jerryzhu.androidexplore.di.qualifier.WanAndroidUrl;
import com.jerryzhu.androidexplore.utils.AnimatorUtil;
import com.jerryzhu.androidexplore.utils.CommonUtils;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author : jerryzhu
 * <p>
 * Time : 2018/12/15
 * <p>
 * Description : this is HttpModule
 */
@Module
public class HttpModule {

    @Singleton
    @Provides
    GeekApis provideGeekApis(@WanAndroidUrl Retrofit retrofit){

        return retrofit.create(GeekApis.class);
    }

    @Singleton
    @Provides
    @WanAndroidUrl
    Retrofit provideGeekApisRetrofit(Retrofit.Builder builder, OkHttpClient client){

        return cretateRetrofit(builder,client,GeekApis.HOST);

    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder(){
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkhttpClientBuilder(){
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient provideOkhttpClient(OkHttpClient.Builder builder){

        if(BuildConfig.DEBUG){
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(httpLoggingInterceptor);
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 60);

//      设置缓存
        builder.cache(cache);
        builder.addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR);
        builder.addInterceptor(REWRITE_RESPONSE_INTERCEPTOR_OFFLINE);
//      设置超时
        builder.connectTimeout(10,TimeUnit.SECONDS);
        builder.readTimeout(20,TimeUnit.SECONDS);
        builder.writeTimeout(20,TimeUnit.SECONDS);
//      失败重连
        builder.retryOnConnectionFailure(true);
//      cookie认证
        builder.cookieJar(new PersistentCookieJar(new SetCookieCache(),new SharedPrefsCookiePersistor(AndroidExploreApp.getInstance())));


        return builder.build();
    }

    private Retrofit cretateRetrofit(Retrofit.Builder builder, OkHttpClient client, String host) {

        return builder.baseUrl(host)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    private final Interceptor REWRITE_RESPONSE_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            String cacheControl = originalResponse.header("Cache-Control");
            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + 5000)
                        .build();
            } else {
                return originalResponse;
            }
        }
    };

    private final Interceptor REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!CommonUtils.isNetworkConnected()) {
                request = request.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached")
                        .build();
            }
            return chain.proceed(request);
        }
    };



}
