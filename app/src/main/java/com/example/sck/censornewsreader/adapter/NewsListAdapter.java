package com.example.sck.censornewsreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sck.censornewsreader.R;
import com.example.sck.censornewsreader.models.Collection1;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Collection1> mListData;
    private LayoutInflater inflater;

    public NewsListAdapter(Context context, List<Collection1> mListData) {
        this.mListData = mListData;
        inflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        NewsItem news;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, null);
            news = new NewsItem();
            news.newsTitle = (TextView) view.findViewById(R.id.newsTitle);
            news.newsDate = (TextView) view.findViewById(R.id.newsDate);
            news.newsImage = (ImageView) view.findViewById(R.id.newsImage);
            view.setTag(news);
        } else {
            news = (NewsItem) view.getTag();
        }

        Collection1 item = mListData.get(position);

        news.newsTitle.setText(item.getTitle().getText());
        news.newsDate.setText(item.getDate());

        if ( item.getImage().getSrc().length() > 1) {
            Picasso.with(mContext).load(item.getImage().getSrc()).into(news.newsImage);
        } else {
            news.newsImage.setImageResource(R.drawable.img_place);
        }
        return view;
    }

    static class NewsItem {
        TextView newsTitle;
        TextView newsDate;
        ImageView newsImage;
    }
}
