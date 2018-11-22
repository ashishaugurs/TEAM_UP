package com.teamup.activity;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelViewPager implements Parcelable {

    int description;
    int header;
    int img;

    public int getDescription(){
        return description;
    }

    public void setDescription(int des){
       this.description=des;
    }

    public int getHeader(){
        return header;
    }

    public void setHeader(int head){
        this.header=head;
    }


    public int getImage(){
        return img;
    }

    public void setImage(int img){
        this.img=img;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.description);
        dest.writeInt(this.header);
        dest.writeInt(this.img);
    }

    public ModelViewPager() {
    }

    protected ModelViewPager(Parcel in) {
        this.description = in.readInt();
        this.header = in.readInt();
        this.img = in.readInt();
    }

    public static final Creator<ModelViewPager> CREATOR = new Creator<ModelViewPager>() {
        @Override
        public ModelViewPager createFromParcel(Parcel source) {
            return new ModelViewPager(source);
        }

        @Override
        public ModelViewPager[] newArray(int size) {
            return new ModelViewPager[size];
        }
    };
}
