package com.zb.zblibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zb.library.util.ViewUtil;
import com.zb.zblibrary.R;
import com.zb.zblibrary.model.SwitchVideoModel;

import java.util.List;

/**
 * Created by acer on 2017/9/12.
 */

public class SwitchVideoTypeDialog extends Dialog {

    private Context mContext;

    private ListView listview = null;

    private ArrayAdapter<SwitchVideoModel> adapter = null;

    private List<SwitchVideoModel> data;

    private OnListItemClickListener onListItemClickListener;

    public SwitchVideoTypeDialog(@NonNull Context context) {
        super(context,R.style.dialog_style);
    }

    public SwitchVideoTypeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SwitchVideoTypeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void initList(List<SwitchVideoModel> data,OnListItemClickListener onListItemClickListener){
        this.onListItemClickListener = onListItemClickListener;
        this.data = data;

        View view = ViewUtil.getInstanceFor(getContext()).inflate().inflate(R.layout.switch_video_dialog,null);
        listview = (ListView) view.findViewById(R.id.switch_dialog_list);
        setContentView(view);
        adapter = new ArrayAdapter<SwitchVideoModel>(mContext,R.layout.switch_video_dialog_item,data);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new OnItemClickListener());

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        lp.width = (int) (dm.widthPixels*0.8);
        dialogWindow.setAttributes(lp);
    }

    private class OnItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            dismiss();
            onListItemClickListener.onItemClick(position);
        }
    }

    public interface OnListItemClickListener{
        void onItemClick(int position);
    }
}
