import java.rmi.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Client{

    // utilizzo scanner per lettura input utente
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String [] args){
        // dichiarazione nome servizio al quale ci si vuole collegare
        String nomeServizio="ServerCalc";
        // inizializzazione di variabili di servizio
        float valoriCalcolo[] = new float[2];
        float risultatoCalcolo = 0;
        int sceltaMenu;
        // prima rchiesta di inserimento pwd prima di procedere
        stampaLineaVuota();
        System.out.println("Inserisci password per accedere al servizio CALCOLATRICE_DISTRIBUITA : (-> pwd <-)");
        // lettura pwd
        String password = leggiStringa();
        try{
            // collegamento a oggetto remoto tramite lookup del nome assegnatogli
            InterfCalcolatrice calcolatrice = (InterfCalcolatrice)Naming.lookup(nomeServizio);
            // controllo correttezza credenziali
            while(!calcolatrice.controlloAutorizzazione(calcolaHash(password))){
                System.out.println("!!! Password errata riprovare ( -> pwd <- ) !!!");
                password = leggiStringa();
            }
            // ciclo infinito fino a volontà dell'utente di uscire
            while(true){
                // stampa delle possibili opzioni e operazioni selezionabili
                stampaMenu();
                // inizializzazione di una lista di operandi nuova all'inizio di ogni ciclo
                ArrayList<Float> operandi = new ArrayList<>();
                // stampa del risultato parziale se ha un valore diverso da 0
                if(parzialeEsiste(risultatoCalcolo)){
                    // aggiunta in testa del valore parziale nella lista degli operandi in modo che venga sempre compreso 
                    //  (la gestione avviene in maniera diversa per quanto riguarda l'operazione di divisione)
                    stampaParziale(risultatoCalcolo);
                    operandi.add(risultatoCalcolo);
                }
                // lettura da input di cosa vuole fare l'utente
                sceltaMenu=leggiSceltaMenu();
                stampaLineaVuota();
                // gestione della scelta 
                if(sceltaMenu==1){
                    // somma
                    System.out.println("+ hai scelto somma +");
                    // invocazione della funzione per la lettura di una lista di operandi da aggiungere a lista
                    leggiPiuOperandi(operandi);
                    System.out.println(" + + + + +");
                    // assegnazione risultato a variabile
                    risultatoCalcolo = calcolatrice.sommaPiuOperandi(operandi);
                }else if(sceltaMenu==2){
                    // sottrazione
                    System.out.println("- hai scelto sottrazione -");
                    // invocazione della funzione per la lettura di una lista di operandi da aggiungere a lista
                    leggiPiuOperandi(operandi);
                    System.out.println(" - - - - -");
                    // assegnazione risultato a variabile
                    risultatoCalcolo = calcolatrice.sottrazionePiuOperandi(operandi);
                }else if(sceltaMenu==3){
                    // prodotto
                    System.out.println("* hai scelto prodotto *");
                    // invocazione della funzione per la lettura di una lista di operandi da aggiungere a lista
                    leggiPiuOperandi(operandi);
                    System.out.println(" * * * * *");
                    // assegnazione risultato a variabile
                    risultatoCalcolo = calcolatrice.prodottoPiuOperandi(operandi);
                }else if(sceltaMenu==4){
                    // divisione
                    System.out.println(": hai scelto divisione :");
                    // gestione del valore parziale
                    if(parzialeEsiste(risultatoCalcolo)){
                        // se esiste viene assegnato al dividendo
                        valoriCalcolo[0] = risultatoCalcolo;
                        // si richiede inserimento di un divisore
                        System.out.println(" Inserisci divisore : ");
                        valoriCalcolo[1] = leggiFloat();
                    }else{
                        // se parziale non esiste si richiede di inserire entrambi i valori della divisione
                        valoriCalcolo = leggiDueOperandi();
                    }
                    // si fa controllo sul secondo valore per evitare che sia 0
                    controlloDivisionePerZero(valoriCalcolo);
                    System.out.println(" : : : : :");
                    // assegnazione risultato a variabile
                    risultatoCalcolo = calcolatrice.divisione(valoriCalcolo[0], valoriCalcolo[1]);
                }else if(sceltaMenu==5){
                    // valore del parziale viene resettato se l'utente sceglie 5
                    risultatoCalcolo = 0;
                }else if(sceltaMenu==0){
                    // exit
                    scanner.close();
                    System.exit(0);
                }
                // stampa risultato a seguito di qualunque sia l'operazione fatta
                if(sceltaMenu>=1 && sceltaMenu<=4){
                    System.out.println("Risultato = " + risultatoCalcolo);
                }
                stampaLineaVuota();
            }
        // cattura delle eccezioni generate
        }catch(Exception e){
            System.out.println("ClientException : " + e.getMessage());
            e.printStackTrace();
        }

    }

    private static ArrayList<Float> leggiPiuOperandi(ArrayList<Float> operandi) {
        boolean flag = true; 
        String valore;
        while(flag || operandi.isEmpty()){
            flag=true;
            System.out.println("Inserisci operando : (= per terminare)");
            valore = leggiStringa();
            //System.out.println("Stringa letta : " + valore);
            if(valore.contains("=")){
                flag = false;
            }else{
                while(!isFloat(valore)){
                    System.out.println("Valore inserito non valido inseriscine un'altro : (= per terminare)");
                    valore = leggiStringa();
                    if(valore.contains("=")){
                        flag = false;
                        break;
                    }
                }
                if(isFloat(valore)){
                    float ultimoOperandoLetto = Float.parseFloat(valore);
                    operandi.add(ultimoOperandoLetto);
                }
            }
        }
        return operandi;
    }

    private static boolean parzialeEsiste(float risultatoCalcolo) {
        if(risultatoCalcolo==0){
            return false;
        }
        return true;
    }

    private static void stampaParziale(float risultatoCalcolo) {
            stampaLineaVuota();
            System.out.println("Risultato parziale -> " + risultatoCalcolo);
            stampaLineaVuota();
    }

    private static void stampaMenu() {
        stampaLineaVuota();
        System.out.println("1.Esegui Somma");
        System.out.println("2.Esegui Sottrazione");
        System.out.println("3.Esegui Prodotto");
        System.out.println("4.Esegui Divisione");
        System.out.println("5.RESET PARZIALE");
        System.out.println("0.Esci");
    }

    private static String hashString(String input, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] hashedBytes = md.digest(input.getBytes());
            // Convertire i byte hash in una rappresentazione esadecimale
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String calcolaHash(String password){
        String hash = hashString(password, "SHA-256");
        return hash;
    }

    public static ArrayList<Float> leggiPiuOperandi(){
        boolean flag = true; 
        String valore;
        ArrayList<Float> operandi = new ArrayList<>();
        while(flag || operandi.isEmpty()){
            flag=true;
            System.out.println("Inserisci operando : (= per terminare)");
            valore = leggiStringa();
            if(valore.contains("=")){
                flag = false;
            }else{
                while(!isFloat(valore)){
                    System.out.println("Valore inserito non valido inseriscine un'altro : (= per terminare)");
                    valore = leggiStringa();
                    if(valore.contains("=")){
                        flag = false;
                        break;
                    }
                }
                if(isFloat(valore)){
                    float ultimoOperandoLetto = Float.parseFloat(valore);
                    operandi.add(ultimoOperandoLetto);
                }
            }
        }
        return operandi;
    }

    public static boolean isFloat(String valore) {
        try{
            Float.parseFloat(valore);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public static String leggiStringa(){
        String stringa = scanner.next();
        return stringa.trim(); 
    }

    public static void controlloDivisionePerZero(float[] valoriCalcolo) {
        while (valoriCalcolo[1]==0) {
            System.out.println("!!! Non puoi inserire 0 al denominatore, inserisci un'altro valore !!!");
            valoriCalcolo[1] = leggiFloat();
        }
    }

    public static void stampaLineaVuota(){
        System.out.println("");
    }

    public static float[] leggiDueOperandi(){
        System.out.println("Inserisci il primo valore");
        float a = leggiFloat();
        System.out.println("Inserisci il secondo valore");
        float b = leggiFloat();
        return new float[]{a,b};
    }

    public static float leggiFloat(){
        while(!scanner.hasNextFloat()) {
            System.out.println("!!! Input non valido. Inserisci un numero valido !!!");
            scanner.next();
        }
        float valoreFloat = scanner.nextFloat();
        return valoreFloat;
    }

    public static int leggiSceltaMenu(){
        while(!scanner.hasNextInt()) {
            System.out.println("!!! Input non valido. Inserisci un numero compreso tra 0 e 4 !!!");
            scanner.next();
        }
        int valoreInserito = scanner.nextInt();
        while(valoreInserito>=6 || valoreInserito<=-1){
            System.out.println("!!! Input non valido. Inserisci un numero compreso tra 0 e 4 !!!");
            valoreInserito = scanner.nextInt();
        }
        return valoreInserito;
    }
}