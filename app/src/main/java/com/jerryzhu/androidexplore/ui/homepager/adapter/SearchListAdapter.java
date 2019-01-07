package com.jerryzhu.androidexplore.ui.homepager.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.core.bean.mainpager.collect.FeedArticleData;
import java.util.List;

public class SearchListAdapter extends BaseQuickAdapter<FeedArticleData,BaseViewHolder> {

    boolean isCollectPage = false;

    boolean isNightMode = false;

    boolean isSearchPage = false;

    public void isCollectPage() {
        isCollectPage = true;
        notifyDataSetChanged();
    }

    public void isNightMode(boolean isNightMode) {
        this.isNightMode = isNightMode;
        notifyDataSetChanged();
    }


    public void isSearchPage(){
        isSearchPage = true;
        notifyDataSetChanged();
    }



    public SearchListAdapter(int layoutResId, @Nullable List<FeedArticleData> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, FeedArticleData article) {

        if(!TextUtils.isEmpty(article.getTitle())){
            helper.setText(R.id.item_home_pager_title,Html.fromHtml(article.getTitle()));
        }
        if(article.isCollect() || isCollectPage){
            helper.setImageResource(R.id.item_home_pager_like_iv,R.drawable.icon_like);
        }else{
            helper.setImageResource(R.id.item_home_pager_like_iv,R.drawable.icon_like_article_not_selected);
        }
        if (!TextUtils.isEmpty( article.getAuthor())){
            helper.setText(R.id.item_home_pager_author,article.getAuthor());
        }

        setTag(helper,article);

        if (!TextUtils.isEmpty(article.getChapterName())) {
            String classifyName = article.getSuperChapterName() + " / " + article.getChapterName();
            if (isCollectPage) {
                helper.setText(R.id.item_home_pager_chapterName, article.getChapterName());
            } else {
                helper.setText(R.id.item_home_pager_chapterName, classifyName);
            }
        }
        if (!TextUtils.isEmpty(article.getNiceDate())) {
            helper.setText(R.id.item_home_pager_niceDate, article.getNiceDate());
        }
        if (isSearchPage) {
            CardView cardView = helper.getView(R.id.item_home_pager_group);
            cardView.setForeground(null);
            if (isNightMode) {
                cardView.setBackground(ContextCompat.getDrawable(mContext, R.color.card_color));
            } else {
                cardView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.selector_search_item_bac));
            }
        }

        helper.addOnClickListener(R.id.item_home_pager_like_iv);
        helper.addOnClickListener(R.id.item_home_pager_chapterName);
        helper.addOnClickListener(R.id.item_home_pager_tag_read_tv);


    }

    private void setTag(BaseViewHolder helper, FeedArticleData article) {
        helper.getView(R.id.item_home_pager_tag_green_tv).setVisibility(View.INVISIBLE);
        helper.getView(R.id.item_home_pager_tag_read_tv).setVisibility(View.INVISIBLE);
        if(isCollectPage){
            return;
        }
        if(article.getSuperChapterName().contains(mContext.getString(R.string.project))){
            setRedTag(helper,R.string.project);
        }
        if(article.getSuperChapterName().contains(mContext.getString(R.string.navigation))){
            setRedTag(helper,R.string.navigation);
        }

        setGreenTag(helper, article);

    }

    private void setGreenTag(BaseViewHolder helper, FeedArticleData article) {
        if(article.getNiceDate().contains(mContext.getString(R.string.minute))
                || article.getNiceDate().contains(mContext.getString(R.string.hour))
                || article.getNiceDate().contains(mContext.getString(R.string.one_day))){

            helper.getView(R.id.item_home_pager_tag_green_tv).setVisibility(View.VISIBLE);
            helper.setText(R.id.item_home_pager_tag_green_tv,R.string.text_new);
            helper.setTextColor(R.id.item_home_pager_tag_green_tv,ContextCompat.getColor(mContext,R.color.light_green));
            helper.setBackgroundRes(R.id.item_home_pager_tag_green_tv,R.drawable.shape_tag_green_background_normal);
        }
    }

    private void setRedTag(BaseViewHolder helper, int tagName) {
        helper.getView(R.id.item_home_pager_tag_read_tv).setVisibility(View.VISIBLE);
        helper.setText(R.id.item_home_pager_tag_read_tv,tagName);
        helper.setTextColor(R.id.item_home_pager_tag_read_tv,ContextCompat.getColor(mContext,R.color.deep_red));
        helper.setBackgroundRes(R.id.item_home_pager_tag_read_tv,R.drawable.shape_tag_read_background_normal);

    }
}