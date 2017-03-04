package com.example.gtr.sevenweather.bean;

/**
 * Created by GTR on 2017/3/3.
 */

public class Forecast {

    /**
     * cond : {"txt_d":"多云"}
     * date : 2017-03-04
     * tmp : {"max":"18","min":"9"}
     */

    private CondBean cond;
    private String date;
    private TmpBean tmp;

    public CondBean getCond() {
        return cond;
    }

    public void setCond(CondBean cond) {
        this.cond = cond;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TmpBean getTmp() {
        return tmp;
    }

    public void setTmp(TmpBean tmp) {
        this.tmp = tmp;
    }

    public static class CondBean {
        /**
         * txt_d : 多云
         */

        private String txt_d;

        public String getTxt_d() {
            return txt_d;
        }

        public void setTxt_d(String txt_d) {
            this.txt_d = txt_d;
        }
    }

    public static class TmpBean {
        /**
         * max : 18
         * min : 9
         */

        private String max;
        private String min;

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }
    }
}
