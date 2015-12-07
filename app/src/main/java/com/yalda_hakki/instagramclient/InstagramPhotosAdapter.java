package com.yalda_hakki.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by yaldahakki on 2015-12-06.
 */
public class InstagramPhotosAdapter extends ArrayAdapter <InstagramPhoto> {
    public InstagramPhotosAdapter(Context context,  List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }
    //what our items look like
    //Use the template to display each photo
    @Override
    public View getView(int position,View convertView, ViewGroup parent){
        //get the data item for this position
        InstagramPhoto photo = getItem(position);
        //check if we are using a recycled view, if not we need to inflate
        if ( convertView == null ){
            //create a new view from the template
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo,parent,false);
        }
        //Lookup the views for populating in the data (image,caption)
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = ( ImageView) convertView.findViewById(R.id.ivPhoto);
        //insert the model data into each of the view items
        tvCaption.setText(photo.caption);
        //clear out the imageView
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);

        //return the converted item as a view
        return convertView;
    }
}
