import java.rmi.*;
import java.util.ArrayList;

public interface InterfCalcolatrice extends Remote{

    public boolean controlloAutorizzazione(String token) throws RemoteException;

    public boolean controlloToken(String token) throws RemoteException;

    public float prodottoPiuOperandi(ArrayList<Float>operandi) throws RemoteException;

    public float sottrazionePiuOperandi(ArrayList<Float>operandi) throws RemoteException;

    public float sommaPiuOperandi(ArrayList<Float>operandi) throws RemoteException;

    public float somma(float a , float b) throws RemoteException;

    public float sottrazione(float a , float b) throws RemoteException;

    public float prodotto(float a , float b) throws RemoteException;

    public float divisione(float a , float b) throws RemoteException;
    
}