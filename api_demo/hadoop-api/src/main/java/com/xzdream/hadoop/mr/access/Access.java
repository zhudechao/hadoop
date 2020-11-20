package com.xzdream.hadoop.mr.access;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 自定义复杂数据类型
 */
public class Access implements Writable {
    private String phone;//手机号码
    private long up;//上行流量
    private long down;//下行流量
    private long sum;//流量总和

    public String toString(){
        return "Access{"+
                "phone='"+phone+"'"+
                ", up="+up+
                ", down="+down+
                ", sum="+sum
                +"}";
    }

    public Access(){}

    public Access(String phone,long up,long down){
        this.phone = phone;
        this.up = up;
        this.down = down;
        this.sum = up+down;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(phone);
        dataOutput.writeLong(up);
        dataOutput.writeLong(down);
        dataOutput.writeLong(sum);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.phone = dataInput.readUTF();
        this.up = dataInput.readLong();
        this.down = dataInput.readLong();
        this.sum = dataInput.readLong();
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public long getUp(){
        return up;
    }

    public void setUp(long up){
        this.up = up;
    }

    public long getDown(){
        return down;
    }

    public void setDown(long down){
        this.down = down;
    }

    public long getSum(){
        return sum;
    }

    public void setSum(long sum){
        this.sum = sum;
    }


}
