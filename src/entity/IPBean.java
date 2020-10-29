package entity;

public class IPBean {
    public static final int TYPE_HTTP = 0;
    public static final int TYPE_HTTPS = 1;

    private int id;
    private String ip;
    private String loc;
    private int port;
    private String type;

    public IPBean(IPBean bean){
        ip = bean.getIp();
        port = bean.getPort();
        type = bean.getType();
    }

    public IPBean(String ip, int port, String type) {
        this.ip = ip;
        this.port = port;
        this.type = type;
    }

    @Override
    public String toString() {
        return "IPBean{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", loc='" + loc + '\'' +
                ", port=" + port +
                ", type='" + type + '\'' +
                '}';
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
