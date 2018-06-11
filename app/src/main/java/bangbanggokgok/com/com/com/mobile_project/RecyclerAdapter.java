package bangbanggokgok.com.com.com.mobile_project;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private List<RecyclerItem> list;
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    // 생성자 구현
    public RecyclerAdapter(List<RecyclerItem> recyclerItems){
        this.list = recyclerItems;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_holder, parent, false);
        return new RecyclerViewHolder(view);
    }
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
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
