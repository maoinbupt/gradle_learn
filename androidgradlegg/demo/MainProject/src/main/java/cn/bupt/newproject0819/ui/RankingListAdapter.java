package cn.bupt.newproject0819.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.bupt.newproject0819.R;
import cn.bupt.newproject0819.model.GameInfoModel;

public class RankingListAdapter<T> extends GameCenterBaseAdapter<T> {
    private Context mcontext;

    public RankingListAdapter(Context context) {
        super(context);
        mcontext = context;
    }

    @SuppressWarnings({"unchecked", "incomplete-switch"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_sohu_game_center_ranking_list_adapter, parent, false);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.sohu_game_center_ranking_list_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final GameInfoModel appInfo = (GameInfoModel) getItem(position);

		/* 设置标题 */
        holder.tvTitle.setText(appInfo.getName());


        return convertView;
    }

    private class ViewHolder {
        private TextView tvTitle;
    }


}
