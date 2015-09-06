package hero.huy.smssync.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import hero.huy.smssync.model.CommandMessage;
import hero.huy.smssync.model.CommandType;
import hero.huy.smssync.model.ProClient;
import hero.huy.smssync.util.ConstantValues;
import hero.huy.smssync.util.Logger;

public class SmsSyncService extends Service {

    private boolean mIsRunning;
    private DatagramSocket mUdpSocket;
    private Thread mWaitClient;
//    private Runnable mRunnable = new Runnable() {
//        @Override
//        public void run() {
//            try {
//                mUdpSocket = new DatagramSocket(ConstantValues.PORT);
//                while (mIsRunning) {
//                    try {
//                        byte[] buffer = new byte[ConstantValues.MAX_LENGTH_PACKET];
//                        DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
//                        mUdpSocket.receive(incoming);
//                        CommandMessage message = new CommandMessage(new String(incoming.getData(), 0, incoming.getLength(), "UTF-8"));
//                        switch (message.getType()) {
//                            case CommandType.IN_COMING_SMS:
//                                break;
//                            case CommandType.OUT_SMS:
//                                break;
//                        }
//                    } catch (Exception e) {
//                        Logger.e("SmsSyncService.startListening Exception in while");
//                        e.printStackTrace();
//                    }
//                }
//            } catch (SocketException e) {
//                Logger.e("SmsSyncService.startListening SocketException");
//                e.printStackTrace();
//            }
//        }
//    };;

    public SmsSyncService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d("In SmsSyncService.onCreate");

        try {
            mIsRunning = true;
            startListening();
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Logger.e("SmsSyncService.onCreate Exception");
            e.printStackTrace();
        }

        Logger.d("Out SmsSyncService.onCreate");
    }

    private void startListening() {
        Logger.d("In SmsSyncService.startListening");
        mWaitClient=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mUdpSocket = new DatagramSocket(ConstantValues.PORT);
                    while (mIsRunning) {
                        try {
                            byte[] buffer = new byte[ConstantValues.MAX_LENGTH_PACKET];
                            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
                            mUdpSocket.receive(incoming);
                            CommandMessage message = new CommandMessage(new String(incoming.getData(), 0, incoming.getLength(), "UTF-8"));
                            switch (message.getType()) {
                                case CommandType.IN_COMING_SMS:
                                    break;
                                case CommandType.OUT_SMS:
                                    break;
                            }
                        } catch (Exception e) {
                            Logger.e("SmsSyncService.startListening Exception in while");
                            e.printStackTrace();
                        }
                    }
                } catch (SocketException e) {
                    Logger.e("SmsSyncService.startListening SocketException");
                    e.printStackTrace();
                }
            }
        });
        mWaitClient.start();
        Logger.d("Out SmsSyncService.startListening");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d("In SmsSyncService.onStartCommand");
        if (intent.getAction()!=null){
            CommandMessage cmd = (CommandMessage) intent.getSerializableExtra(ConstantValues.EXTRA_COMMAND_MESSAGE);
            if (intent.getAction().equals(ConstantValues.ACTION_IN_SMS)){
                new ProClient(cmd,mUdpSocket).start();
            }
        }
        Logger.d("Out SmsSyncService.onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Logger.d("In SmsSyncService.onDestroy");
        closeServer();
        super.onDestroy();
        Logger.d("Out SmsSyncService.onDestroy");
    }
    public void closeServer() {
        Logger.d("In SmsSyncService.closeServer");
        try {
            mIsRunning = false;
            mUdpSocket.close();
        } catch (Exception e) {
            Logger.e("SmsSyncService.closeServer exception");
            e.getStackTrace();
        }
        Logger.d("Out SmsSyncService.closeServer");
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
