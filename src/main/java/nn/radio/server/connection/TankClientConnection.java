package nn.radio.server.connection;

import nn.radio.dto.KeyEventDto;
import nn.radio.dto.MouseEventDto;
import nn.radio.dto.TankDto;
import nn.radio.server.KeyEventListener;
import nn.radio.server.MouseClickedListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class TankClientConnection extends Thread {
    private static final int TANK_OUT_PORT = 4446;
    Socket clientSocket;
    ServerSocket serverSocket;
    Socket socket;
    ObjectOutputStream objectOutputStreamSender;
    ObjectInputStream reciever;
    private boolean isAlive = true;
    private boolean isConnected = false;

    public TankClientConnection (){
        System.out.println("TankClientConnection");
    }

    private void startClientSocket () {
        while (!isConnected) {
            try {
                System.out.println("TankClientConnection Socket");
                clientSocket = new Socket("localhost", TANK_OUT_PORT);
                System.out.println("TankClientConnection Socket 4444");
            } catch (Exception e) {
                System.out.println("TankClientConnection Socket error");
            }

            try {
                System.out.println("TankClientConnection ObjectOutputStream");
                objectOutputStreamSender = new ObjectOutputStream(clientSocket.getOutputStream());
                System.out.println("TankClientConnection ObjectOutputStream OK");
                isConnected = true;
            } catch (Exception e) {
                System.out.println("TankClientConnection objectOutputStreamSender error");
            }

            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateTankMapWithDto (Map<String, TankDto> map) {
        try {
            objectOutputStreamSender.writeObject(map);
        } catch (Exception e) {
            System.out.println("TankClientConnection updateTankMapWithDto error");
        }
    }

    @Override
    public void run(){
            System.out.println("TankClientConnection run");
            startClientSocket();
            System.out.println("TankClientConnection startClientSocket");


//        while (isAlive){
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

}
