package cn.lemon.bs;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.lemon.bs.data.bean.Notice;
import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;

/**
 * Created by linlongxin on 2017.5.17.
 */

public class NoticeAdapter extends RecyclerAdapter<Notice> {

    public NoticeAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new NoticeViewHolder(parent);
    }

    class NoticeViewHolder extends BaseViewHolder<Notice> {

        private TextView title, intro;

        public NoticeViewHolder(ViewGroup parent) {
            super(parent, R.layout.holder_notice);
        }

        @Override
        public void onInitializeView() {
            super.onInitializeView();
            title = findViewById(R.id.title);
            intro = findViewById(R.id.intro);
        }

        @Override
        public void setData(Notice data) {
            super.setData(data);
            title.setText(data.title);
            intro.setText(data.intro);
        }
    }
}
