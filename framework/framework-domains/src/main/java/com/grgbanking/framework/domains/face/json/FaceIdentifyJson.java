package com.grgbanking.framework.domains.face.json;

import java.io.Serializable;

/**
 * Created by zhangweihua on 2018/1/4.
 */
public class FaceIdentifyJson implements Serializable{
    private String uid;
    private String similarity;
    private String threshold;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSimilarity() {
        return similarity;
    }

    public void setSimilarity(String similarity) {
        this.similarity = similarity;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }
}
