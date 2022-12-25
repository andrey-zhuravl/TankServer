package nn.radio.server.model;

import nn.radio.dto.KeyEventDto;
import nn.radio.dto.MouseEventDto;

import java.awt.event.KeyEvent;

import static nn.radio.Constants.*;

public class ServerTank {
    public String id;
    public float Y;
    public float X;
    public float alpha = 0.0F;
    public boolean alive = true;

    float deltaX = 0.0F;
    float deltaY = 0.0F;
    float deltaAlpha = 0.0F;
    float speedAlpha = 6.4F;
    float speed = 9.45F;

    public static float TANK_HEIGHT = 109F;
    public static float TANK_WIDTH = 82F;
    public static int BG_BORDER = 3;



    private boolean isFocusable;
    private ServerTore tore;


    public void move () {
        if (X >= SCENA_WIDTH - TANK_HEIGHT - 10) {
            deltaX = 0;
            deltaY = 0;
            X = SCENA_WIDTH - TANK_HEIGHT - 15;
        }
        if (X < SCENA_BORDER) {
            deltaX = 0;
            deltaY = 0;
            X = SCENA_BORDER + 15;
        }

        if (Y >= SCENA_HEIGTH - TANK_WIDTH - 10) {
            deltaX = 0;
            deltaY = 0;
            Y = SCENA_HEIGTH - TANK_WIDTH - 15;
        }

        if (Y < SCENA_BORDER) {
            deltaX = 0;
            deltaY = 0;
            Y = SCENA_BORDER + 15;
        }

        X = X + deltaX;
        Y = Y + deltaY;
        alpha = alpha + deltaAlpha;
        tore.move((X + TANK_HEIGHT / 2.4F), (Y + TANK_WIDTH / 3));
    }

    public boolean isFocusable () {
        return isFocusable;
    }

    public void setFocusable (boolean focusable) {
        isFocusable = focusable;
    }

    public ServerTore getTore () {
        return tore;
    }

    public void setTore (ServerTore tore) {
        this.tore = tore;
    }

    public void keyEventPressed (KeyEventDto e) {
        System.out.println("keyEventPressed");
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT: {
                deltaAlpha = -speedAlpha;
                break;
            }
            case KeyEvent.VK_RIGHT: {
                deltaAlpha = speedAlpha;
                break;
            }
            case KeyEvent.VK_UP: {
                deltaX = (float) Math.cos(Math.toRadians(alpha)) * speed;
                deltaY = (float) Math.sin(Math.toRadians(alpha)) * speed;
                break;
            }
            case KeyEvent.VK_DOWN: {
                deltaX = -(float) Math.cos(Math.toRadians(alpha)) * speed;
                deltaY = -(float) Math.sin(Math.toRadians(alpha)) * speed;
                break;
            }
            case KeyEvent.VK_Q: {
                tore.turnContrClockArrowDirection();
                break;
            }
            case KeyEvent.VK_W: {
                tore.turnByClockArrowDirection();
                break;
            }
            case KeyEvent.VK_SPACE: {
                System.out.println("shoot alpha = " + alpha);
                tore.shoot(alpha);
                break;
            }
        }
    }

    public void keyEventReleased (KeyEventDto e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT: {
                deltaAlpha = 0.0F;
                break;
            }
            case KeyEvent.VK_RIGHT: {
                deltaAlpha = 0.0F;
                break;
            }
            case KeyEvent.VK_UP: {
                deltaX = 0;
                deltaY = 0;
                break;
            }
            case KeyEvent.VK_DOWN: {
                deltaX = 0;
                deltaY = 0;
                break;
            }
            case KeyEvent.VK_Q: {
                tore.zeroSpeedAlpha();
                break;
            }
            case KeyEvent.VK_W: {
                tore.zeroSpeedAlpha();
                break;
            }
        }
    }

    public void mouseEventClicked (MouseEventDto e) {
        if ((e.getPoint().getX() <= X + TANK_HEIGHT)
                && (e.getPoint().getX() >= X)
                && (e.getPoint().getY() <= Y + TANK_WIDTH)
                && (e.getPoint().getY() >= Y)
        ) {
            setFocusable(true);
        } else {
            setFocusable(false);
        }
    }

    public boolean intersect (ServerCharge clientCharge) {
        if ((clientCharge.X <= X + TANK_HEIGHT)
                && (clientCharge.X >= X)
                && (clientCharge.Y <= Y + TANK_WIDTH)
                && (clientCharge.Y >= Y)
        ) {
            return true;
        }
        return false;
    }
}
