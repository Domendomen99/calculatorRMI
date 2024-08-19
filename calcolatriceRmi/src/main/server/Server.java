import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    
    public static void main(String [] args){
        try{
            // assegnazione nome al servizio che andremo a fornire tramite il server
            String nomeServizio="ServerCalc";
            // inizializzazione di oggetto che gestira le chiamate a calcolatrice
            ImplCalcolatrice implCalcolatrice = new ImplCalcolatrice();
            // esportazione dell'oggetto in modo che sia possibile invocarlo in remoto 
            // - exportObject restituisce riferimento a oggetto remoto e viene assegnato all'interfaccia in modo che oggetti in rete possan interagirci e accedere alle funzionalità di ImplCalcolatrice
            InterfCalcolatrice interfCalcolatrice = (InterfCalcolatrice)UnicastRemoteObject.exportObject(implCalcolatrice, 0);
            // collegamento gestore chiamate servizio e nome
            Naming.rebind(nomeServizio, interfCalcolatrice);
            // messaggi di attesa del server
            System.out.println();
            System.out.println(nomeServizio + " : bound in registry");
            System.out.println("Server in attesa ... ");
            System.out.println();
        // gestione delle possibili eccezioni sollevate dalle chiamate di metodi remoti
        }catch(Exception e){
            System.out.println("ServerException : " + e.getMessage());
            e.printStackTrace();
        }
    }

}