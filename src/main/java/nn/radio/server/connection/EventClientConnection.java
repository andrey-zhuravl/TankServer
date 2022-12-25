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

public class EventClientConnection extends Thread {
    private static final int KEY_EVENT_IN_PORT = 4447;
    Socket clientSocket;
    ServerSocket serverSocket;
    Socket socket;
    ObjectOutputStream objectOutputStreamSender;
    ObjectInputStream reciever;
    private boolean isAlive = true;
    private KeyEventListener keyEventListener;
    private MouseClickedListener mouseClickedListener;
    private boolean isConnected = false;

    public EventClientConnection (){
        System.out.println("EventClientConnection");
    }

    private void startServerSocket () {
        while (!isConnected) {
            try {
                serverSocket = new ServerSocket(KEY_EVENT_IN_PORT);
            } catch (Exception e) {
                System.out.println("EventClientConnection ServerSocket error");
            }

            try {
                socket = serverSocket.accept();
            } catch (Exception e) {
                System.out.println("EventClientConnection accept error");
            }

            try {
                reciever = new ObjectInputStream(socket.getInputStream());
                isConnected = true;
            } catch (Exception e) {
                System.out.println("EventClientConnection ObjectInputStream error");
            }

            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setKeyEventListener(KeyEventListener listener){
        this.keyEventListener = listener;
    }

    public void setMouseClickedListener(MouseClickedListener listener){
        this.mouseClickedListener = listener;
    }

    @Override
    public void run(){
        System.out.println("EventClientConnection run");
        startServerSocket();
        System.out.println("EventClientConnection startClientSocket");

        eventListenerCycle();
    }

    private void eventListenerCycle () {
        while (isAlive){
            try {
                Object event = reciever.readObject();
                if(event instanceof KeyEventDto) {
                    KeyEventDto e = (KeyEventDto) event;
                    switch (e.paramString) {
                        case "KEY_PRESSED":
                            keyPressed(e);
                            break;
                        case "KEY_RELEASED":
                            keyReleased(e);
                            break;
                        default:
                            break;
                    }
                } else if (event instanceof MouseEventDto){
                    MouseEventDto e = (MouseEventDto) event;
                    System.out.println("EventClientConnection MouseEventDto, X = " + e.x + ", Y=" + e.y);
                    mouseClicked(e);
                }
            } catch (Exception ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void keyPressed (KeyEventDto e) {
        keyEventListener.keyPressed(e);
    }

    public void keyReleased (KeyEventDto e) {
        keyEventListener.keyReleased(e);
    }

    public void mouseClicked (MouseEventDto e) {
        mouseClickedListener.mouseClicked(e);
    }
}
