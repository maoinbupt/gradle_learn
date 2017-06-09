package cn.bupt.newproject0819.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 清理适配器抽象类，封装了基本的inflater以及常用方法
 *
 * @param <T>
 */
public abstract class GameCenterBaseAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mList;

    public GameCenterBaseAdapter(Context context) {
        this.mContext = context;
        mList = new ArrayList<T>();

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mList != null ? mList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return mList != null ? position : -1;
    }

    /**
     * 在原有数据基础上刷新数据
     *
     * @param list
     */
    public void refreshData(List<T> list) {
        if (list != null) {
            synchronized (mList) {
                mList.addAll(list);
                notifyDataSetChanged();
            }
        }
    }

    /**
     * 清除原有数据刷新新数据
     *
     * @param list
     */
    public void cleanAndRefreshData(List<T> list) {
        if (list != null) {
            synchronized (mList) {
                mList.clear();
                mList.addAll(list);
                notifyDataSetChanged();
            }
        }
    }

    /**
     * 清除所有数据
     */
    public void reset() {
        if (mList != null) {
            mList.clear();
            notifyDataSetChanged();
        }

    }

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> mList) {
        this.mList = mList;
    }

}
