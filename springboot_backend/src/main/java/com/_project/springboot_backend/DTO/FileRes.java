package com._project.springboot_backend.DTO;
import java.util.List;

//文件操作返回的结果类
public class FileRes {
    //0失败1成功
    int code;
    String error_msg;
    //二进制数据
    List<byte[]> lst_bytes;


    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getError_msg() {
        return error_msg;
    }
    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
    public List<byte[]> getLst_bytes() {
        return lst_bytes;
    }
    public void setLst_bytes(List<byte[]> lst_bytes) {
        this.lst_bytes = lst_bytes;
    }
}
