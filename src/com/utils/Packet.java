package com.utils;

import java.io.Serializable;
import java.math.BigInteger;
public class Packet implements Serializable
{
    public BigInteger id;
    public String data;
    public Packet(BigInteger id, String data)
    {
        this.id = id;
        this.data = new String(data);
    }
    public Packet()
    {
        //this.id = id;
        //this.data = new String(data);
    }
    
    public BigInteger getId()
    {
        return id;
    }
    public String getData()
    {
        return data;
    }
    public void setId(BigInteger id)
    {
        this.id = id;
    }
    public void setData(String data)
    {
        this.data = new String(data);
    }
    public void setPacket(BigInteger type, String data)
    {
        /*switch(type)
        {
            code here
        }*/
        this.data = new String(data);
    }
}