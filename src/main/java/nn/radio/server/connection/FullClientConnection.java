package nn.radio.server.connection;

import nn.radio.TankiProperty;

public class FullClientConnection {
    public EventClientConnection eventClientConnection;
    public TankClientConnection tankClientConnection;
    TankiProperty property;

    public FullClientConnection (TankiProperty property) {
        this.property = property;
    }

    public FullClientConnection (EventClientConnection eventClientConnection, TankClientConnection tankClientConnection) {
        this.tankClientConnection = tankClientConnection;
        this.eventClientConnection = eventClientConnection;

    }
}
