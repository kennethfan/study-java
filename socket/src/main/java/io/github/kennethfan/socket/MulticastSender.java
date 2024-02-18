package io.github.kennethfan.socket;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSender {

    public static void main(String[] args) {
        try {
            // 定义组播地址和端口
            InetAddress group = InetAddress.getByName("224.0.0.1");
            int port = 18888;

            // 创建 MulticastSocket
            MulticastSocket multicastSocket = new MulticastSocket(port);

            // 加入组播组
            multicastSocket.joinGroup(group);

            // 发送数据
            String message = "Hello, multicast!";
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, group, port);
            multicastSocket.send(packet);

            // 关闭 MulticastSocket
            multicastSocket.leaveGroup(group);
            multicastSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
