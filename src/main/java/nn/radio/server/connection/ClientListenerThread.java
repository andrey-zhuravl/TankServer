package nn.radio.server.connection;

import nn.radio.TankiProperty;
import nn.radio.server.KeyEventListener;
import nn.radio.server.MouseClickedListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientListenerThread extends Thread {
    private static final int KEY_EVENT_IN_PORT = 14472;
    private static final int TANK_OUT_PORT = 14473;
    private static final String IP = "192.168.1.10";
    private static final String LOCAL_IP = "127.0.0.1";
    ServerSocket eventServerSocket;
    ServerSocket tankServerSocket;
    TankiProperty property;

    private boolean isAlive = true;
    private KeyEventListener keyEventListener;
    private MouseClickedListener mouseClickedListener;
    private boolean isConnected = false;
    public List<FullClientConnection> fullClientConnectionList;

    public ClientListenerThread(TankiProperty property,
                                KeyEventListener keyEventListener,
                                MouseClickedListener mouseClickedListener){
        this.property = property;
        fullClientConnectionList = new ArrayList<>();
        this.keyEventListener = keyEventListener;
        this.mouseClickedListener = mouseClickedListener;
    }

    @Override
    public void run () {
        try {
            eventServerSocket = new ServerSocket(Integer.valueOf(property.get("SERVER_EVENT_PORT")));
            tankServerSocket = new ServerSocket(Integer.valueOf(property.get("SERVER_TANK_PORT")));
        } catch (Exception e) {
            System.out.println("ClientListenerThread ServerSocket error");
        }
        while (isAlive) {
            receiveNewConnection();
        }
        try {
            eventServerSocket.close();
            System.out.println("ClientListenerThread close");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveNewConnection(){
        try {
            EventClientConnection eventClientConnection = getEventClientConnection();
            TankClientConnection tankClientConnection = getTankClientConnection();
            fullClientConnectionList.add(new FullClientConnection(
                    eventClientConnection,
                    tankClientConnection));
           eventClientConnection.start();

            System.out.println("ClientListenerThread startServerSocket create new connection");
        } catch (Exception e) {
            System.out.println("ClientListenerThread startServerSocket error");
        }
    }

    private TankClientConnection getTankClientConnection () throws IOException {
        Socket tankSocket = tankServerSocket.accept();
        ObjectOutputStream objectSender = new ObjectOutputStream(tankSocket.getOutputStream());
        System.out.println("ClientListenerThread objectSender");
        var tConn = new TankClientConnection(tankSocket, objectSender);
        return tConn;
    }

    private EventClientConnection getEventClientConnection () throws IOException {
        Socket eventSocket = eventServerSocket.accept();
        System.out.println("ClientListenerThread serverSocket.accept()");

        ObjectInputStream objectReciever = new ObjectInputStream(eventSocket.getInputStream());
        ObjectOutputStream objectSender = new ObjectOutputStream(eventSocket.getOutputStream());
        System.out.println("ClientListenerThread objectReciever");
        EventClientConnection eventClientConnection = new EventClientConnection(eventSocket, objectReciever,
                objectSender,
                keyEventListener,
                mouseClickedListener);
        return eventClientConnection;
    }
}
