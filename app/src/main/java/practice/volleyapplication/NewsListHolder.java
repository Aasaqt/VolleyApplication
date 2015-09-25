package practice.volleyapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by aasaqt on 23/9/15.
 */
public class NewsListHolder extends RecyclerView.ViewHolder {
    TextView username;
    TextView rating;
    TextView remarks;
    ImageView profile;
    RelativeLayout item;


    public NewsListHolder(View itemView) {
        super(itemView);
        username = (TextView)itemView.findViewById(R.id.username);
        rating = (TextView)itemView.findViewById(R.id.rating);
        remarks = (TextView)itemView.findViewById(R.id.remarks);
        profile = (ImageView)itemView.findViewById(R.id.iv_user);
        item = (RelativeLayout) itemView.findViewById(R.id.item);
    }
}
