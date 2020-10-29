package utils;

import entity.IPBean;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class browse {

    public boolean f(IPBean ipBean,String u) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ipBean.getIp(), ipBean.getPort()));
        try {
            URLConnection httpCon = new URL(u).openConnection(proxy);
            httpCon.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.81 Safari/537.36");
//            httpCon.setConnectTimeout(10000);
//            httpCon.setReadTimeout(10000);
            int code = ((HttpURLConnection) httpCon).getResponseCode();
            System.out.println("成功访问！");
            return code == 200;
        } catch (IOException e) {
            System.out.println("代理失效:"+e);
        }
        return false;
    }
}
