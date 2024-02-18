package io.github.kennethfan.socket;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastReceiver {

    public static void main(String[] args) {
        try {
            // 定义组播地址和端口
            InetAddress group = InetAddress.getByName("224.0.0.1");
            int port = 18888;

            // 创建 MulticastSocket
            MulticastSocket multicastSocket = new MulticastSocket(port);

            // 加入组播组
            multicastSocket.joinGroup(group);

            // 接收数据
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            multicastSocket.receive(packet);

            // 处理接收到的数据
            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received: " + message);

            // 关闭 MulticastSocket
            multicastSocket.leaveGroup(group);
            multicastSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
