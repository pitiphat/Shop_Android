package com.example.guide.shop;

import java.util.List;

/**
 * Created by guide on 27/5/2560.
 */

public class YoutubeResult {
    private List<YoutubesBean> youtubes;

    public List<YoutubesBean> getYoutubes() {
        return youtubes;
    }

    public void setYoutubes(List<YoutubesBean> youtubes) {
        this.youtubes = youtubes;
    }

    public static class YoutubesBean {
        /**
         * id : 1
         * name : item1
         * price : 50
         * img : item_1.png
         * create_date : 222
         */

        private String id;
        private String name;
        private String price;
        private String img;
        private String create_date;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }
    }
}
