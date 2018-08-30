
package com.example.puza.friendrecommendation.model.InterestsDAO;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Interests implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    public final static Parcelable.Creator<Interests> CREATOR = new Creator<Interests>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Interests createFromParcel(Parcel in) {
            return new Interests(in);
        }

        public Interests[] newArray(int size) {
            return (new Interests[size]);
        }

    }
    ;
    private final static long serialVersionUID = 3958864284044138396L;

    protected Interests(Parcel in) {
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.data, (com.example.puza.friendrecommendation.model.InterestsDAO.Datum.class.getClassLoader()));
    }

    public Interests() {
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(message);
        dest.writeList(data);
    }

    public int describeContents() {
        return  0;
    }

}
