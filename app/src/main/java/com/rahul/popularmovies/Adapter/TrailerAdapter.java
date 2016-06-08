package com.rahul.popularmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rahul.popularmovies.R;
import com.rahul.popularmovies.Model.Trailer;
import com.rahul.popularmovies.Utility.Constants;

import java.util.ArrayList;

/**
 * Created by rahulkumarlohra on 07/06/16.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    Context context;
    ArrayList<Trailer> arrayList;
    public TrailerAdapter(Context context, ArrayList<Trailer> arrayList)
    {
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final String text = arrayList.get(position).getName();
        final String id = String.valueOf(arrayList.get(position).getKey());
        holder.textView.setText(arrayList.get(position).getName());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.YOUTUBE_VIDEO_URL.concat(id))));
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rl);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);
            textView = (TextView) itemView.findViewById(R.id.tv);

        }
    }
}
