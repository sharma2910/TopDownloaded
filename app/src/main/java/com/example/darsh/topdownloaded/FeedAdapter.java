package com.example.darsh.topdownloaded;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FeedAdapter extends ArrayAdapter {

    private static final String TAG = "FeedAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<FeedEntry> applications;

    public FeedAdapter(Context context, int resource,List<FeedEntry> applications) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.applications = applications;
    }

    @Override
    public int getCount() {
        return applications.size();
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = layoutInflater.inflate(layoutResource,parent,false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tvArtist = (TextView) convertView.findViewById(R.id.tv_artist);
        ImageView image = (ImageView) convertView.findViewById(R.id.tvImg);

        FeedEntry currentApp = applications.get(position);
        tvName.setText(currentApp.getName());
        tvArtist.setText(currentApp.getArtist());
        Picasso.get().load(currentApp.getImgUrl()).resize(500, 500).centerCrop().into(image);

        return convertView;
    }

    private class ViewHolder {

        final TextView tvName;
        final TextView tvArtist;
        final ImageView tvImg;

        ViewHolder(View v){
            this.tvName = v.findViewById(R.id.tv_name);
            this.tvArtist = v.findViewById(R.id.tv_artist);
            this.tvImg = v.findViewById(R.id.tvImg);
        }
    }
}