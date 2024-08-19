import java.rmi.RemoteException;
import java.util.ArrayList;

public class ImplCalcolatrice implements InterfCalcolatrice{

    @Override
    public float somma(float a, float b) throws RemoteException {
        return a+b;
    }

    @Override
    public float sottrazione(float a, float b) throws RemoteException{
        return a-b;
    }

    @Override
    public float prodotto(float a, float b) throws RemoteException{
        return a*b;
    }

    @Override
    public float divisione(float a, float b) throws RemoteException{
        if(b==0){
            return -1;
        }
        return a/b;
    }

    @Override
    public float sommaPiuOperandi(ArrayList<Float> operandi) throws RemoteException {
        float risultato = 0;
        for (Float operando : operandi) {
            risultato = risultato + operando;
        }
        return risultato;
    }

    @Override
    public float prodottoPiuOperandi(ArrayList<Float> operandi) throws RemoteException {
        float risultato = operandi.get(0);
        for(int i = 1; i<operandi.size();i++){
            risultato = risultato*operandi.get(i);
        }
        return risultato;
    }

    @Override
    public float sottrazionePiuOperandi(ArrayList<Float> operandi) throws RemoteException {
        float risultato = operandi.get(0);
        for(int i = 1; i<operandi.size();i++){
            risultato = risultato-operandi.get(i);
        }
        return risultato;
    }

    public boolean controlloAutorizzazione(String token) throws RemoteException{
        try{
            if(!controlloToken(token)){
                throw new RemoteException("Non si è autorizzati a fare questa operazione");
            }  
        }catch(RemoteException e){
            System.out.println("Password errata");
            return false;
        }
        return true;
    }

    @Override
    public boolean controlloToken(String token) throws RemoteException {
        String tokenValido = "a1159e9df3670d549d04524532629f5477ceb7deec9b45e47e8c009506ecb2c8";
        if(token.equals(tokenValido)){
            return true;
        }
        return false;
    }
    
    
}
