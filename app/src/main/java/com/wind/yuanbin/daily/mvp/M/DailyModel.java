package com.wind.yuanbin.daily.mvp.M;

/**
 * Created by Wind on 2018/2/5.
 */
import java.util.List;


public class DailyModel  {

    private String date;
    private List<Stories> stories;
    private List<Top_stories> top_stories;
    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }

    public void setStories(List<Stories> stories) {
        this.stories = stories;
    }
    public List<Stories> getStories() {
        return stories;
    }

    public void setTop_stories(List<Top_stories> top_stories) {
        this.top_stories = top_stories;
    }
    public List<Top_stories> getTop_stories() {
        return top_stories;
    }

    /**
     *
     */
    public class Stories {

        private List<String> images;
        private int type;
        private long id;
        private String ga_prefix;
        private String title;
        public void setImages(List<String> images) {
            this.images = images;
        }
        public List<String> getImages() {
            return images;
        }

        public void setType(int type) {
            this.type = type;
        }
        public int getType() {
            return type;
        }

        public void setId(long id) {
            this.id = id;
        }
        public long getId() {
            return id;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }
        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

    }
    /**
     *
     */
    public class Top_stories {

        private String image;
        private int type;
        private long id;
        private String ga_prefix;
        private String title;
        public void setImage(String image) {
            this.image = image;
        }
        public String getImage() {
            return image;
        }

        public void setType(int type) {
            this.type = type;
        }
        public int getType() {
            return type;
        }

        public void setId(long id) {
            this.id = id;
        }
        public long getId() {
            return id;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }
        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

    }
}
