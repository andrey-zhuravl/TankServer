package nn.radio.server;

import nn.radio.dto.KeyEventDto;

public interface KeyEventListener {
    void keyPressed (KeyEventDto e);

    void keyReleased (KeyEventDto e);
}
