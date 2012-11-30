package net.unit8.bootstrap.customize.action;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class IndexAction extends ActionSupport {

    public String execute() throws Exception {
        return "success";
    }

    public String message;

    public String hello() throws Exception {
        message = "いえいえこちらこそこんにちは。";
        return "hello";
    }
}
