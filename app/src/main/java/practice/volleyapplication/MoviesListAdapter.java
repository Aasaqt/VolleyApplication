package practice.volleyapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by aasaqt on 23/9/15.
 */
public class MoviesListAdapter extends RecyclerView.Adapter<MovieListHolder> {
    Context mContext;
    ArrayList<MovieListModel> list;

    public MoviesListAdapter(Context c,ArrayList<MovieListModel> list){
        this.mContext  =  c;
        this.list = list;
    }
    @Override
    public MovieListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.movie_list_item,parent ,false);
        MovieListHolder mh = new MovieListHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(MovieListHolder holder, int position) {
        final MovieListModel item = list.get(position);
        holder.username.setText(item.title);
        holder.remarks.setText(item.title);
        holder.rating.setText(""+item.getRating());


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ExtendedListActivity.class);
                intent.putExtra("location_id", ""+item.getTitle());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
