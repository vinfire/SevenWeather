package com.example.gtr.sevenweather.bean;

/**
 * Created by GTR on 2017/3/3.
 */

public class Basic {


    /**
     * city : 苏州
     * id : CN101190401
     * update : {"loc":"2017-03-03 16:51"}
     */

    private String city; //cityName
    private String id; //weatherId
    private Update update;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public static class Update {
        /**
         * loc : 2017-03-03 16:51
         */

        private String loc;  //更新时间

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }
    }
}
