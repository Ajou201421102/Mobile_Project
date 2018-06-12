package bangbanggokgok.com.com.com.mobile_project;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class NavigateAdapter extends RecyclerView.Adapter<NavigateHolder> {
    private List<NavigateItem> list;
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    // 생성자 구현
    public NavigateAdapter(List<NavigateItem> navigateItems){
        this.list = navigateItems;
    }


    @Override
    public NavigateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigate_item_holder, parent, false);
        return new NavigateHolder(view);
    }
    @Override
    public void onBindViewHolder(NavigateHolder holder, final int position) {
        holder.bindData(list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position);
            }
        });
        // holder에 아이템 binding
        // holder의 아이템에 listener 기능 등록

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}