package hero.huy.smssync.model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import hero.huy.smssync.util.ConstantValues;
import hero.huy.smssync.util.Logger;

/**
 * Created by huybu on 9/6/2015.
 */
public class ProClient extends Thread {
    private DatagramSocket mUdpSocket;
    private CommandMessage mCmd;

    public ProClient(CommandMessage message,DatagramSocket udpSocket){
        mCmd=message;
        mUdpSocket=udpSocket;
    }

    @Override
    public void run() {
        Logger.d("IN ProClient.run");
        try {
            byte[] sendBuf = mCmd.getBytes();
            DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, InetAddress.getByName(mCmd.getReceiAddress()), ConstantValues.PORT);
            mUdpSocket.send(packet);
        } catch (IOException e) {
            Logger.e("ProClient.run IOException");
            e.printStackTrace();
        }
        Logger.d("OUT ProClient.run");
    }
}
