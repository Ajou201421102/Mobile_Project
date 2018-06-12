package bangbanggokgok.com.com.com.mobile_project;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigateHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {

    private TextView title;
    private ImageView image;


    public NavigateHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.navigate_title);
        image = itemView.findViewById(R.id.navigate_image);
        // 뷰와 인스턴스 연결
    }

    public void bindData(NavigateItem recyclerItem) {
        title.setText(recyclerItem.getTitle());
        image.setImageBitmap(recyclerItem.getImage());
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
