package com.walmart.gtulla.nyts;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by gtulla on 5/29/16.
 */
public class ArticleArrayAdapter extends ArrayAdapter<Article> {
    public ArticleArrayAdapter(Context context, List<Article> articles) {
        super(context, android.R.layout.simple_list_item_1, articles);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Article article = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_article_result, parent, false);
           // convertView.setTag(holder);
        }
        ImageView imgView = (ImageView)convertView.findViewById(R.id.ivImage);
        TextView title = (TextView)convertView.findViewById(R.id.tvTitle);
        // ImageView imgView = holder.imgView;
        imgView.setImageResource(0);
        String thumbNail = article.getThumbNail();
        if(!TextUtils.isEmpty(thumbNail)) {
            Picasso.with(getContext()).load(thumbNail).
                    transform(new RoundedCornersTransformation(20, 20)).into(imgView);
        }
        title.setText(article.getHeadLine());
        return convertView;
    }
}
