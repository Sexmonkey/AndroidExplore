package com.jerryzhu.androidexplore.core.http;

import com.jerryzhu.androidexplore.core.bean.mainpager.banner.BannerData;
import com.jerryzhu.androidexplore.core.bean.BaseResponse;
import com.jerryzhu.androidexplore.core.bean.mainpager.collect.FeedArticleListData;
import com.jerryzhu.androidexplore.core.bean.mainpager.login.LoginData;
import com.jerryzhu.androidexplore.core.bean.mainpager.useful.UsefulData;

import java.util.List;
import io.reactivex.Observable;


public interface HttpHelper {

    /**
     * 广告栏
     * http://www.wanandroid.com/banner/json
     *
     * @return 获取轮播数据
     */

    Observable<BaseResponse<List<BannerData>>> getBannerData();

    /**
     *
     * http://www.wanandroid.com/banner/json
     *
     * @return 站内文章数据
     */
    Observable<BaseResponse<FeedArticleListData>> getFeedArticleList(int num);

    /**
     * 取消站内文章
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     *
     * @param id article id
     * @return 取消站内文章数据
     */
    Observable<BaseResponse<FeedArticleListData>> cancelCollectArticle(int id);

    /**
     * 收藏站内文章
     * http://www.wanandroid.com/lg/collect/1165/json
     *
     * @param id article id
     * @return 注册数据
     */
    Observable<BaseResponse<FeedArticleListData>> addCollectArticle(int id);


    /**
     * 登陆
     * http://www.wanandroid.com/user/login
     *
     * @param username user name
     * @param password password
     * @return 项目类别数据
     */
    Observable<BaseResponse<LoginData>> getLoginData(String username, String password);


    /**
     * 取消收藏页面站内文章
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     *
     *
     * @param
     * @param id article id
     * @return 取消收藏页面站内文章数据
     */
    Observable<BaseResponse<FeedArticleListData>> cancelCollectPageArticle(int id);

    /**
     * 退出登录
     * http://www.wanandroid.com/user/logout/json
     */
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
    Observable<BaseResponse<LoginData>> getRegisterdata(String username, String password, String confirmPassword);

    /**
     * 常用网站
     * http://www.wanandroid.com/friend/json
     *
     * @return 常用网站数据
     */
    Observable<BaseResponse<List<UsefulData>>> getUserfulSites();
}
