package nn.radio.dto;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class MouseEventDto extends EventDto implements Serializable {
    public int x;
    public int y;

    public Point getPoint() {
        return new Point(x, y);
    }


}
