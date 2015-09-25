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
public class NewsListAdapter extends RecyclerView.Adapter<NewsListHolder> {
    Context mContext;
    ArrayList<NewsListModel> list;

    public NewsListAdapter(Context c, ArrayList<NewsListModel> list){
        this.mContext  =  c;
        this.list = list;
    }
    @Override
    public NewsListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.movie_list_item,parent ,false);
        NewsListHolder mh = new NewsListHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(NewsListHolder holder, int position) {
        final NewsListModel item = list.get(position);
        holder.username.setText(item.heading);
        holder.remarks.setText(item.description);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ExtendedListActivity.class);
                intent.putExtra("location_id", ""+item.getPost_id());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
