package com.example.gtr.sevenweather.bean;

/**
 * Created by GTR on 2017/3/3.
 */

public class Suggestion {


    /**
     * comf : {"txt":"白天天气晴好，早晚会感觉偏凉，午后舒适、宜人。"}
     * cw : {"txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"}
     * sport : {"txt":"天气较好，但风力较大，推荐您进行室内运动，若在户外运动请注意避风保暖。"}
     */

    private ComfBean comf; //comfort
    private CwBean cw; //carWash
    private SportBean sport;

    public ComfBean getComf() {
        return comf;
    }

    public void setComf(ComfBean comf) {
        this.comf = comf;
    }

    public CwBean getCw() {
        return cw;
    }

    public void setCw(CwBean cw) {
        this.cw = cw;
    }

    public SportBean getSport() {
        return sport;
    }

    public void setSport(SportBean sport) {
        this.sport = sport;
    }

    public static class ComfBean {
        /**
         * txt : 白天天气晴好，早晚会感觉偏凉，午后舒适、宜人。
         */

        private String txt;

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }
    }

    public static class CwBean {
        /**
         * txt : 较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。
         */

        private String txt;

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }
    }

    public static class SportBean {
        /**
         * txt : 天气较好，但风力较大，推荐您进行室内运动，若在户外运动请注意避风保暖。
         */

        private String txt;

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }
    }
}
