package bangbanggokgok.com.com.com.mobile_project;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {
    private TextView seq;
    private TextView title;
    private ImageView image;
    private TextView desc;
    private TextView place;
    private TextView realmName;
    private TextView endDate;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        seq = itemView.findViewById(R.id.itme_seq);
        title = itemView.findViewById(R.id.item_title);
        image = itemView.findViewById(R.id.item_image);
        place = itemView.findViewById(R.id.item_place);
        realmName = itemView.findViewById(R.id.item_realmName);
        endDate = itemView.findViewById(R.id.itme_end);
        // 뷰와 인스턴스 연결
    }

    public void bindData(RecyclerItem recyclerItem) {
        seq.setText(recyclerItem.getSeq());
        title.setText(recyclerItem.getTitle());
        image.setImageBitmap(recyclerItem.getImage());
        place.setText(recyclerItem.getPlace());
        realmName.setText(recyclerItem.getRealmName());
        endDate.setText(recyclerItem.getEndDate());
        // 뷰와 데이터 바인딩
    }
/*

    @Override
    public void onClick(View v) {

        System.out.println(getPosition());
        Intent intent = new Intent(v.getContext() , InfoDetails.class);
        v.getContext().startActivity(intent);
    }
*/



}

