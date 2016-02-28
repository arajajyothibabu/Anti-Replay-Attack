package com.server;
import com.utils.Packet;
import com.utils.RSA;
import com.utils.Serializer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.math.BigInteger;
import java.lang.*;
 
public class Server extends Serializer {
    DatagramSocket socket = null;
 
    public Server() {
        
    }
 
    public void process() throws Exception
    {
        try {
            socket = new DatagramSocket(9876);
            byte[] incomingData = new byte[1024];
            BigInteger id = BigInteger.valueOf(-1);
            RSA rsa = new RSA();
            BigInteger pubKey = rsa.getPublicKey();
            System.out.println("Key: "+ rsa.getPublicKey());
            Packet response;
            
            while (true) {
                try{
                    DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                    socket.receive(incomingPacket);
                    Packet packet = (Packet) deserialize(incomingPacket.getData());
                    System.out.println("Received message from client: " +packet.id +"\n\t\t\tData:"+ packet.data);
                    InetAddress IPAddress = incomingPacket.getAddress();
                    int port = incomingPacket.getPort();
                    if(packet.id.subtract(id).compareTo(BigInteger.valueOf(1)) == 0)
                    {
                        id = id.add(BigInteger.valueOf(1));
                        if(packet.data.equals("hello"))
                        {
                            response = new Packet(pubKey,"Hello packet recieved..!");
                            System.out.println("Key: "+ rsa.getPublicKey());
                        }
                        else
                        {
                            System.out.println("Decrypted message from client: "+ bytesToString(rsa.decrypt(packet.data.getBytes())));
                            //System.out.println("Key: "+ rsa.getPublicKey());
                            response = new Packet(BigInteger.valueOf(200),"Thank you for the message..!" );
                        }
                    }
                    else
                    {
                        response = new Packet(BigInteger.valueOf(404),"*****Invalid User..!*****");
                    }
                    byte[] data = serialize(response);
                    System.out.println(response.data);
                    DatagramPacket replyPacket =
                            new DatagramPacket(data, data.length, IPAddress, port);
                    socket.send(replyPacket);
                    Thread.sleep(2000);
                    //socket.close();
                }catch (IOException i) {
                    i.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException c) {
                    c.printStackTrace();
                }
            }
 
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
 
    public static void main(String[] args) throws Exception
    {
        Server server = new Server();
        server.process();
    }
}