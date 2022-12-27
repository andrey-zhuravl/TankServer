package nn.radio.server.connection;

public class FullClientConnection {
    public EventClientConnection eventClientConnection;
    public TankClientConnection tankClientConnection;

    public FullClientConnection(){

    }

    public FullClientConnection(EventClientConnection eventClientConnection,
                                TankClientConnection tankClientConnection){
        this.tankClientConnection = tankClientConnection;
        this.eventClientConnection = eventClientConnection;

    }
}
