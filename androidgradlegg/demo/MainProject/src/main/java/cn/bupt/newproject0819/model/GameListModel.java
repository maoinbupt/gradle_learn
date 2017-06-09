package cn.bupt.newproject0819.model;

import java.util.List;

/**
 * Created by GaoFeng on 2017.03.20.
 */

public class GameListModel extends AbstractBaseModel{
    private List<GameInfoModel> data;

    public List<GameInfoModel> getData() {
        return data;
    }

    public void setData(List<GameInfoModel> data) {
        this.data = data;
    }
}
