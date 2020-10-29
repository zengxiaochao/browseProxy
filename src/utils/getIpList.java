package utils;

import entity.IPBean;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class getIpList {

    Mysql mysql = new Mysql();
    List<IPBean> allList = mysql.getList();

    public static void main(String[] args) throws Exception {
        new getIpList().getAllList(500);
        new getIpList().getAllList(430);
        new getIpList().getAllList(560);
        new getIpList().getAllList(590);
        new getIpList().getAllList(410);
        new getIpList().getAllList(430);
        new getIpList().getAllList(450);
        new getIpList().getAllList(480);
    }
    public void getAllList(int index) throws InterruptedException {
        int temp = index;
        String URL = "https://www.kuaidaili.com/free/intr/";
        String body = Utils.getResponseContent(URL+temp+"/");
        while(temp<index+20)
        {
            temp++;
            Thread.sleep(1010);
            body+=Utils.getResponseContent(URL+temp+"/");
        }
        List<IPBean> ipList = getList(body);
        for (int i=0;i<ipList.size();i++)
        {
            IPBean ip = ipList.get(i);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if(isValid(ip.getIp(),ip.getPort()))
                    {
                        if(isOnly(ip.getIp()))
                        {
                            mysql.insertIp(ip);
                            System.out.print(ip.getIp()+":"+ip.getPort());
                            System.out.println("有效");
                        }
                    }
                }
            }).start();
        }
        return ;
    }

    /**
     * 唯一Ip
     */
    public boolean isOnly(String ip){
        for(int i=0;i<allList.size();i++)
        {
            if(allList.get(i).getIp().equals(ip))
            {
                System.out.println(ip+"重复");
                return false;
            }
        }
        return true;
    }
    /**
     * 获取ip、端口、位置
     * @param body
     * @return
     */
    public static List<IPBean> getList(String body){
        List<IPBean> list = new ArrayList<IPBean>();
        List<String> ipList = getIp(body);
        List<String> portList = getPort(body);
        List<String> typeList = getType(body);
        List<String> locList = getLoc(body);
        for(int i=0;i<ipList.size();i++)
        {
            IPBean ipBean = new IPBean(ipList.get(i),Integer.valueOf(portList.get(i)),typeList.get(i));
            ipBean.setLoc(locList.get(i));
            list.add(ipBean);
        }
        return list;
    }
    public static List<String> getIp(String body){
        List<String> ipList = new ArrayList<>(); //故用集合来存储结果
        Pattern ipCompile = null;
        ipCompile = Pattern.compile("<td data-title=\"IP\">.*?</td>"); // 正则
        Matcher ipMatcher = ipCompile.matcher(body);
        while (ipMatcher.find()){
            String ip = ipMatcher.group();
            String start = "\"IP\">";
            String end = "</td>";
            int s = ip.indexOf(start) + start.length();
            int e = ip.indexOf(end);
            ipList.add(ip.substring(s, e));
        }
        return ipList;
    }
    public static List<String> getType(String body){
        List<String> typeList = new ArrayList<>(); //故用集合来存储结果
        Pattern compile = null;
        compile = Pattern.compile("<td data-title=\"类型\">.*?</td>"); // 正则
        Matcher matcher = compile.matcher(body);
        while (matcher.find()){
            String port = matcher.group();
            String start = "\"类型\">";
            String end = "</td>";
            int s = port.indexOf(start) + start.length();
            int e = port.indexOf(end);
            typeList.add(port.substring(s, e));
        }
        return typeList;
    }
    public static List<String> getPort(String body){
        List<String> portList = new ArrayList<>(); //故用集合来存储结果
        Pattern compile = null;
        compile = Pattern.compile("<td data-title=\"PORT\">.*?</td>"); // 正则
        Matcher matcher = compile.matcher(body);
        while (matcher.find()){
            String port = matcher.group();
            String start = "\"PORT\">";
            String end = "</td>";
            int s = port.indexOf(start) + start.length();
            int e = port.indexOf(end);
            portList.add(port.substring(s, e));
        }
        return portList;
    }
    public static List<String> getLoc(String body){
        List<String> locList = new ArrayList<>(); //故用集合来存储结果
        Pattern compile = null;
        compile = Pattern.compile("<td data-title=\"位置\">.*?</td>"); // 正则
        Matcher matcher = compile.matcher(body);
        while (matcher.find()){
            String loc = matcher.group();
            String start = "\"位置\">";
            String end = "</td>";
            int s = loc.indexOf(start) + start.length();
            int e = loc.indexOf(end);
            locList.add(loc.substring(s, e));
        }
        return locList;
    }
    /**
     * 检测代理ip是否有效
     *
     * @return
     */
    public static boolean isValid(String ip,int port) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
        try {
            URLConnection httpCon = new URL("https://www.baidu.com/").openConnection(proxy);
            httpCon.setConnectTimeout(5000);
            httpCon.setReadTimeout(5000);
            int code = ((HttpURLConnection) httpCon).getResponseCode();
            return code == 200;
        } catch (Exception e) {
        }
        return false;
    }
}
