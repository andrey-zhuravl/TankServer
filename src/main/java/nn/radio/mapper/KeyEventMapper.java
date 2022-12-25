package nn.radio.mapper;

import nn.radio.dto.KeyEventDto;

import java.awt.event.KeyEvent;

public class KeyEventMapper {

    public static KeyEventDto fromKeyEvent(KeyEvent e){
        KeyEventDto dto = new KeyEventDto();
        dto.setKeyCode(e.getKeyCode());
        dto.paramString = e.paramString();
        return dto;
    }
}
