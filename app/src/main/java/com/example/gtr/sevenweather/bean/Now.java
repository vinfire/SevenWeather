package com.example.gtr.sevenweather.bean;

/**
 * Created by GTR on 2017/3/3.
 */

public class Now {


    /**
     * cond : {"txt":"多云"}
     * tmp : 13
     */

    private CondBean cond;
    private String tmp; //temperature

    public CondBean getCond() {
        return cond;
    }

    public void setCond(CondBean cond) {
        this.cond = cond;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public static class CondBean {
        /**
         * txt : 多云
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
