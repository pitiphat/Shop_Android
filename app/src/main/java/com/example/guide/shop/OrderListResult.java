package com.example.guide.shop;

import java.util.List;

/**
 * Created by guide on 30/5/2560.
 */

public class OrderListResult {

    /**
     * result : true
     * data : [{"id":"1","table_id":"3","items_id":"1","amount":"3","status":"0","name":"item1","price":"50","img":"item_1.png","create_date":"222","total_per_item":150},{"id":"2","table_id":"3","items_id":"2","amount":"2","status":"0","name":"item2","price":"50","img":"item_2.png","create_date":"111","total_per_item":100},{"id":"3","table_id":"3","items_id":"3","amount":"1","status":"0","name":"item3","price":"50","img":"item_3.png","create_date":"111","total_per_item":50}]
     * message :
     * error_message :
     */

    private boolean result;
    private String message;
    private String error_message;
    private List<DataBean> data;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * table_id : 3
         * items_id : 1
         * amount : 3
         * status : 0
         * name : item1
         * price : 50
         * img : item_1.png
         * create_date : 222
         * total_per_item : 150
         */

        private String id;
        private String table_id;
        private String items_id;
        private String amount;
        private String status;
        private String name;
        private String price;
        private String img;
        private String create_date;
        private int total_per_item;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTable_id() {
            return table_id;
        }

        public void setTable_id(String table_id) {
            this.table_id = table_id;
        }

        public String getItems_id() {
            return items_id;
        }

        public void setItems_id(String items_id) {
            this.items_id = items_id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public int getTotal_per_item() {
            return total_per_item;
        }

        public void setTotal_per_item(int total_per_item) {
            this.total_per_item = total_per_item;
        }
    }
}
