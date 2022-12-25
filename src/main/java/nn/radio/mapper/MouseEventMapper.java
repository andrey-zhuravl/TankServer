package nn.radio.mapper;

import nn.radio.dto.MouseEventDto;

import java.awt.event.MouseEvent;

public class MouseEventMapper {
    public static MouseEventDto fromMouseEvent(MouseEvent e){
        MouseEventDto dto = new MouseEventDto();
        dto.x = e.getX();
        dto.y = e.getY();
        return dto;
    }
}
