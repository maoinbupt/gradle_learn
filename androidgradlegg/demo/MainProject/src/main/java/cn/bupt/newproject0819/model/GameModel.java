package cn.bupt.newproject0819.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GaoFeng on 2017.03.20.
 */

public class GameModel implements Parcelable {
    private String id;
    private String name;
    private String description;
    private String thumb;
    private String file;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.thumb);
        dest.writeString(this.file);
    }

    public GameModel() {
    }

    protected GameModel(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.thumb = in.readString();
        this.file = in.readString();
    }

    public static final Parcelable.Creator<GameModel> CREATOR = new Parcelable.Creator<GameModel>() {
        @Override
        public GameModel createFromParcel(Parcel source) {
            return new GameModel(source);
        }

        @Override
        public GameModel[] newArray(int size) {
            return new GameModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getThumb() {
        return thumb;
    }

    public String getFile() {
        return file;
    }
}
