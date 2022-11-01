package com.boco.alarmtitle.nodes;


import java.util.Objects;

public class ProcessIpPortVo {

    private String ip;

    private String port;

    private Integer useTimes = 0;

    private Integer index;

    private String subGroup;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Integer getUseTimes() {
        return useTimes;
    }

    public void setUseTimes(Integer useTimes) {
        this.useTimes = useTimes;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(String subGroup) {
        this.subGroup = subGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessIpPortVo that = (ProcessIpPortVo) o;
        return Objects.equals(ip, that.ip) && Objects.equals(port, that.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }


    public String getIpPort() {
        return ip + ":" + port;
    }

    @Override
    public String toString() {
        return "ProcessIpPortVo{" +
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", useTimes=" + useTimes +
                ", index=" + index +
                ", subGroup='" + subGroup + '\'' +
                '}';
    }
}
