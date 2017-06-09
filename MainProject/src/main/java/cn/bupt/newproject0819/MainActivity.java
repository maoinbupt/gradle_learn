package cn.bupt.newproject0819;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.common.sdk.net.connect.http.DaylilyRequest;
import com.common.sdk.net.connect.http.RequestManagerEx;
import com.common.sdk.net.connect.http.center.ErrorType;
import com.common.sdk.net.connect.http.model.DataSession;
import com.common.sdk.net.connect.interfaces.IDataResponseListener;

import java.util.ArrayList;
import java.util.List;

import cn.bupt.newproject0819.model.GameInfoModel;
import cn.bupt.newproject0819.model.GameListModel;
import cn.bupt.newproject0819.tools.DataRequestUtils;
import cn.bupt.newproject0819.tools.DefaultResultParser;
import cn.bupt.newproject0819.ui.RankingListAdapter;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
    
    private static final String TAG = "MainActivity";

    private ListView mPullListView;
    private RankingListAdapter mAdapter;
    private Context mContext;
    private List mDataList = new ArrayList();
    private RequestManagerEx mRequestManager= new RequestManagerEx();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPullListView = (ListView)findViewById(R.id.sohu_game_fragment_ranking_list_listview);
        initData();

    }

    private void initData() {
        DaylilyRequest request = DataRequestUtils.getGameFeedList(0,"",-1,"new","undefined"); // V6.3 改为一页30条
        DefaultResultParser parser = new DefaultResultParser(GameListModel.class);
        mRequestManager.startDataRequestAsync(request, new IDataResponseListener() {
            @Override
            public void onSuccess(Object o, boolean b, DataSession dataSession) {
                LogUtils.d(TAG, "GAOFENG--- onSuccess: ");
                GameListModel data = (GameListModel) o;
                if (data != null && data.getData() != null) {

                    mDataList = data.getData();
                    inflateData();
                }

            }

            @Override
            public void onFailure(ErrorType errorType, DataSession dataSession) {
                LogUtils.d(TAG, "GAOFENG--- onFailure: errorType" + errorType);
            }

            @Override
            public void onCancelled(DataSession dataSession) {
                LogUtils.d(TAG, "GAOFENG--- onCancelled: ");
            }
        }, parser);

    }

    private void inflateData() {
        mContext = MainActivity.this;

        mPullListView.setAdapter(mAdapter);
        mPullListView.setOnItemClickListener(this);

        mAdapter = new RankingListAdapter<>(mContext);
        mAdapter.setList(mDataList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (mDataList.get(position) != null) {
            GameInfoModel gameModel = ((GameInfoModel) (mDataList.get(position)));
			/* 跳转到H5 */
            IntentTools.startWebViewActivity(MainActivity.this,gameModel.getFile());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRequestManager.cancelAllDataRequest();
    }
}
