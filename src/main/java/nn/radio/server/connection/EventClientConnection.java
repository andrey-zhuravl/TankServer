package nn.radio.server.connection;

import nn.radio.dto.KeyEventDto;
import nn.radio.dto.MouseEventDto;
import nn.radio.server.KeyEventListener;
import nn.radio.server.MouseClickedListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class EventClientConnection extends Thread {
    ObjectInputStream reciever;
    private boolean isAlive = true;
    Socket eventSocket;
    private KeyEventListener keyEventListener;
    private MouseClickedListener mouseClickedListener;

    public EventClientConnection (Socket eventSocket, ObjectInputStream reciever,
                                  KeyEventListener keyEventListener,
                                  MouseClickedListener mouseClickedListener) {
        System.out.println("EventClientConnection");
        this.eventSocket = eventSocket;
        this.reciever = reciever;
        this.keyEventListener = keyEventListener;
        this.mouseClickedListener = mouseClickedListener;
    }

    @Override
    public void run () {
        listening();
    }

    private void listening () {
        try {
            eventListeninCycle();
        } catch (Exception ioException) {
            System.out.println("EventClientConnection listening error");
        } finally {
            try {
                eventSocket.close();
            } catch (IOException e) {
                System.out.println("EventClientConnection eventSocket close error1");
            }

            try {
                reciever.close();
            } catch (IOException e) {
                System.out.println("EventClientConnection reciever close error");
            }

            try {
                eventSocket.close();
            } catch (IOException e) {
                System.out.println("EventClientConnection eventSocket close error2");
            }
        }
    }

    private void eventListeninCycle () throws Exception {
        while (isAlive) {
            try {
                Object event = reciever.readObject();
                if (event instanceof KeyEventDto) {
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
                } else if (event instanceof MouseEventDto) {
                    MouseEventDto e = (MouseEventDto) event;
                    mouseClicked(e);
                }
            } catch (Exception ioException) {
                ioException.printStackTrace();
                isAlive = false;
                throw ioException;
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
