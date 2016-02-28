package com.client;

import com.utils.Packet;
import com.utils.RSA;
import com.utils.Serializer;

import java.io.*;
import java.util.*;
import java.net.*;
import java.math.BigInteger;
    
public class Client extends Serializer {
    DatagramSocket Socket;
    
    public Client() {
    
    }
    
    public void createAndListenSocket() throws Exception
    {
        try {
            Socket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");
            byte[] incomingData = new byte[1024];
            Scanner in = new Scanner(System.in);
            int id = 0;
            Random rand = new Random();
            RSA rsa = new RSA();
            boolean acknowledged = false;
            while(true)
            {
                try{
                    while(in.nextLine().equals(""))
                    {
                        System.out.print("Enter Id: ");
                        Packet packet = new Packet();
                        packet.setId(BigInteger.valueOf(in.nextInt()));
                        System.out.print("Enter Data: ");
                        String input = in.next();
                        if(acknowledged)
                        {
                            //System.out.println("debug : " + bytesToString(rsa.encrypt(input.getBytes())));
                            packet.setData(bytesToString(rsa.encrypt(input.getBytes())));
                            //packet.setData(input);
                        }
                        else
                        {
                            packet.setData(input);
                        }
                        byte[] data = serialize(packet);
                        DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);
                        Socket.send(sendPacket);
                        System.out.println("Message sent from client..!");
                        DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                        Socket.receive(incomingPacket);
                        Packet response = (Packet) deserialize(incomingPacket.getData());
                        
                        if(input.equals("hello"))
                        {
                            BigInteger pubKey = response.id;
                            rsa = null;
                            rsa = new RSA(pubKey);
                            System.out.println("Key: "+ rsa.getPublicKey());
                            System.out.println("Public Key Recieved :"+ response.id);
                            acknowledged = true;
                        }
                        System.out.println("\n==>Acknowledgement:" + response.data);
                        System.out.println("Key: "+ rsa.getPublicKey());
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Socket.close();
            }
        
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }catch (SocketException e) {
            e.printStackTrace();
        } 
    }
    
    public static void main(String[] args) throws Exception
    {
        Client client = new Client();
        client.createAndListenSocket();
    }
}