package com.tianqi.demo.app.domain.xml;

public class Root {

    private Resp	resp;

    public Resp getResp() {
        return this.resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }

    @Override
    public String toString() {
        return "Root{" +
                "resp=" + resp +
                '}';
    }
}