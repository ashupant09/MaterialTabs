package android.netcom.ashu.materialtabs.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by KamaL on 29-08-2016.
 */
public class Movies implements Parcelable{

    private long id;
    private String title;
    private Date releaseDateTheater;
    private int audience_score;
    private String synopsis;
    private String thumbnail;
    private String urlSelf;
    private String urlCast;
    private String urlReviews;
    private String urlSimilar;

    public Movies(){

    }
    protected Movies(Parcel in) {
        id = in.readLong();
        title = in.readString();
        long dateMills = in.readLong();
        releaseDateTheater =(dateMills == -1 ? null : new Date(dateMills));
        audience_score = in.readInt();
        synopsis = in.readString();
        thumbnail = in.readString();
        urlSelf = in.readString();
        urlCast = in.readString();
        urlReviews = in.readString();
        urlSimilar = in.readString();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDateTheater() {
        return releaseDateTheater;
    }

    public void setReleaseDateTheater(Date releaseDateTheater) {
        this.releaseDateTheater = releaseDateTheater;
    }

    public int getAudience_score() {
        return audience_score;
    }

    public void setAudience_score(int audience_score) {
        this.audience_score = audience_score;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrlSelf() {
        return urlSelf;
    }

    public void setUrlSelf(String urlSelf) {
        this.urlSelf = urlSelf;
    }

    public String getUrlCast() {
        return urlCast;
    }

    public void setUrlCast(String urlCast) {
        this.urlCast = urlCast;
    }

    public String getUrlReviews() {
        return urlReviews;
    }

    public void setUrlReviews(String urlReviews) {
        this.urlReviews = urlReviews;
    }

    public String getUrlSimilar() {
        return urlSimilar;
    }

    public void setUrlSimilar(String urlSimilar) {
        this.urlSimilar = urlSimilar;
    }

    @Override
    public String toString() {
        return  "ID "+id +
                "title " + title +
                "release date "+ releaseDateTheater +
                "audience score "+ audience_score +
                "synopsis "+ synopsis +
                "Thumbnail "+ thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeLong(releaseDateTheater.getTime());
        dest.writeInt(audience_score);
        dest.writeString(synopsis);
        dest.writeString(thumbnail);
        dest.writeString(urlSelf);
        dest.writeString(urlCast);
        dest.writeString(urlReviews);
        dest.writeString(urlSimilar);
    }
}
