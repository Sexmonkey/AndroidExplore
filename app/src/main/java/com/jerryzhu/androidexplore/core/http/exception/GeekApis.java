package com.jerryzhu.androidexplore.core.http.exception;

import com.jerryzhu.androidexplore.core.bean.BaseResponse;
import com.jerryzhu.androidexplore.core.bean.mainpager.banner.BannerData;
import com.jerryzhu.androidexplore.core.bean.mainpager.collect.FeedArticleListData;
import com.jerryzhu.androidexplore.core.bean.mainpager.login.LoginData;
import com.jerryzhu.androidexplore.core.bean.mainpager.useful.UsefulData;

import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Author : jerryzhu
 * <p>
 * Time : 2018/12/15
 * <p>
 * Description : this is GeekApis
 */

public interface GeekApis {

    String HOST = "http://www.wanandroid.com/";

    /**
     * 获取feed文章列表
     * num 页数
     * @return feed文章列表数据
     */
    @GET("article/list/{num}/json")
    Observable<BaseResponse<FeedArticleListData>> getFeedArticleList(@Path("num") int num);

    /**
     * http://www.wanandroid.com/banner/json
     * homepager banner 广告数据
     * @return 广告数据
     */
    @GET("banner/json")
    Observable<BaseResponse<List<BannerData>>> getBannerData();


    /**
     * 取消收藏页面站内文章
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     *
     * @param id article id
     * @param originId origin id
     * @return 取消收藏页面站内文章数据
     */
    @POST("lg/uncollect_originId/{id}/json")
    @FormUrlEncoded
    Observable<BaseResponse<FeedArticleListData>> cancelCollectArticle(@Path("id") int id, @Field("originId") int originId);

    /**
     * 取消站内文章
     * http://www.wanandroid.com/lg/uncollect/2333/json
     *
     * @param id article id
     * @param originId origin id
     * @return 取消站内文章数据
     */
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    Observable<BaseResponse<FeedArticleListData>> cancelCollectPageArticle(@Path("id") int id,@Field("originId") int originId);

    /**
     * 收藏站内文章
     * http://www.wanandroid.com/lg/collect/1165/json
     *
     * @param id article id
     * @return 注册数据
     */
    @POST("lg/collect/{id}/json")
    Observable<BaseResponse<FeedArticleListData>> addCollectArticle(@Path("id") int id);

    /**
     * 登陆
     * http://www.wanandroid.com/user/login
     *
     * @param username user name
     * @param password password
     * @return 登陆数据
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse<LoginData>> getLoginData(@Field("username") String username, @Field("password") String password);

    /**
     * 退出登录
     * http://www.wanandroid.com/user/logout/json
     * @return
     */
    @GET("user/logout/json")
    Observable<BaseResponse<LoginData>> logout();

    /**
     * 注册
     * http://www.wanandroid.com/user/register
     *
     * @param username user name
     * @param password password
     * @param confirmPassword confirmPassword
     * @return 登陆数据
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<BaseResponse<LoginData>> getRegisterdata(@Field("username") String username,@Field("password") String password,@Field("repassword") String confirmPassword);

    /**
     * 常用网站
     * http://www.wanandroid.com/friend/json
     *
     * @return 常用网站数据
     */
    @GET("friend/json")
    Observable<BaseResponse<List<UsefulData>>> getUsefulData();


}
