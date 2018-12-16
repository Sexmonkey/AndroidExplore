package com.jerryzhu.androidexplore.utils;


import com.jerryzhu.androidexplore.core.bean.BaseResponse;
import com.jerryzhu.androidexplore.core.bean.mainpager.collect.FeedArticleListData;
import com.jerryzhu.androidexplore.core.bean.mainpager.login.LoginData;
import com.jerryzhu.androidexplore.core.http.exception.OtherException;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Author : jerryzhu
 * <p>
 * Time : 2018/12/15
 * <p>
 * Description : this is RxUtils
 */
public class RxUtils {
     /*
     * 统一线程处理
     * @param <T> 指定的泛型类型
     * @return FlowableTransformer
     */
    public static <T> FlowableTransformer<T,T> rxFlSchedulerHelper(){
        return flowable -> flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /*
     * 统一线程处理
     * @param <T> 指定的泛型类型
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<T,T> rxObSchedulerHelper(){

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> Observable) {
                return Observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /*
     * 统一处理返回结果
     * @param <T> 指定的泛型类型
     * @return ObservableTransformer
     */

    public static <T> ObservableTransformer<BaseResponse<T>,T> handleResult(){

        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponse<T>> observable) {
                return observable.flatMap(new Function<BaseResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(BaseResponse<T> baseResponse) throws Exception {

                        if (baseResponse.getErrorCode() == BaseResponse.SUCCESS
                                && baseResponse.getData() !=null
                                && CommonUtils.isNetworkConnected()){

                            return createData(baseResponse.getData());

                        }else{
                            return Observable.error(new OtherException());
                        }
                    }
                });
            }
        };

    }

    /*
     * 统一处理登出结果
     * @param <T> 指定的泛型类型
     * @return ObservableTransformer
     */

    public static <T> ObservableTransformer<BaseResponse<T>,T>  handleLogoutResult(){
        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponse<T>> observable) {
                return observable.flatMap(new Function<BaseResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(BaseResponse<T> baseResponse) throws Exception {

                        if (baseResponse.getErrorCode() == BaseResponse.SUCCESS
                                && CommonUtils.isNetworkConnected()){
                            //创建一个非空数据源，避免onNext()传入null
                            return createData(CommonUtils.cast(new LoginData()));

                        }else{
                            return Observable.error(new OtherException());
                        }
                    }
                });
            }
        };
    }

    /*
     * 统一处理收藏返回结果
     * @param <T> 指定的泛型类型
     * @return ObservableTransformer
     */

    public static <T> ObservableTransformer<BaseResponse<T>, T> handleCollectResult(){

        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponse<T>> observable) {
                return observable.flatMap(new Function<BaseResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(BaseResponse<T> baseResponse) throws Exception {
                        if(baseResponse.getErrorCode() == BaseResponse.SUCCESS
                                && CommonUtils.isNetworkConnected()){

                            //创建一个非空数据源，避免onNext()传入null
                            return createData(CommonUtils.cast(new FeedArticleListData()));

                        }else {

                            return Observable.error(new OtherException());
                        }
                    }
                });
            }
        };

    }


    private static <T> ObservableSource<T> createData(T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                try {

                    e.onNext(data);
                    e.onComplete();

                }catch (Exception exception){
                    e.onError(exception);
                }
            }
        });
    }


}
