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

public class TankClientConnection {
    ObjectOutputStream objectOutputStreamSender;
    public boolean isAlive = true;
    Socket tankSocket;

    public TankClientConnection (Socket tankSocket, ObjectOutputStream objectOutputStreamSender){
        System.out.println("EventClientConnection");
        this.objectOutputStreamSender = objectOutputStreamSender;
    }

    public void updateTankMapWithDto (Map<String, TankDto> map) {
        try {
            objectOutputStreamSender.writeObject(map);
        } catch (Exception e) {
            System.out.println("TankClientConnection updateTankMapWithDto error");
            isAlive = false;

            try {
                tankSocket.close();
            } catch (Exception ioException) {
                ioException.printStackTrace();
                System.out.println("TankClientConnection close tankSocket error");
            }

            try {
                objectOutputStreamSender.close();
            } catch (Exception ioException) {
                ioException.printStackTrace();
                System.out.println("TankClientConnection close objectOutputStreamSender error");
            }

            try {
                tankSocket.close();
            } catch (Exception ioException) {
                ioException.printStackTrace();
                System.out.println("TankClientConnection close tankSocket error");
            }
        }
    }
}
