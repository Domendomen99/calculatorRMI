import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.junit.Test;

public class TestImplCalcolatrice {

    @Test
    public void testSomma() throws RemoteException {
        ImplCalcolatrice calcolatrice = new ImplCalcolatrice();
        assertEquals(5.0f, calcolatrice.somma(2.0f, 3.0f), 0.001);
    }

    @Test
    public void testSottrazione() throws RemoteException {
        ImplCalcolatrice calcolatrice = new ImplCalcolatrice();
        assertEquals(2.0f, calcolatrice.sottrazione(5.0f, 3.0f), 0.001);
    }

    @Test
    public void testProdotto() throws RemoteException {
        ImplCalcolatrice calcolatrice = new ImplCalcolatrice();
        assertEquals(15.0f, calcolatrice.prodotto(3.0f, 5.0f), 0.001);
    }

    @Test
    public void testDivisione() throws RemoteException {
        ImplCalcolatrice calcolatrice = new ImplCalcolatrice();
        assertEquals(2.0f, calcolatrice.divisione(6.0f, 3.0f), 0.001);
        assertEquals(-1.0f, calcolatrice.divisione(6.0f, 0.0f), 0.001);
    }

    @Test
    public void testSommaPiuOperandi() throws RemoteException {
        ImplCalcolatrice calcolatrice = new ImplCalcolatrice();
        ArrayList<Float> operandi = new ArrayList<>();
        operandi.add(2.0f);
        operandi.add(3.0f);
        operandi.add(5.0f);
        assertEquals(10.0f, calcolatrice.sommaPiuOperandi(operandi), 0.001);
    }

    @Test
    public void testProdottoPiuOperandi() throws RemoteException {
        ImplCalcolatrice calcolatrice = new ImplCalcolatrice();
        ArrayList<Float> operandi = new ArrayList<>();
        operandi.add(2.0f);
        operandi.add(3.0f);
        operandi.add(5.0f);
        assertEquals(30.0f, calcolatrice.prodottoPiuOperandi(operandi), 0.001);
    }

    @Test
    public void testSottrazionePiuOperandi() throws RemoteException {
        ImplCalcolatrice calcolatrice = new ImplCalcolatrice();
        ArrayList<Float> operandi = new ArrayList<>();
        operandi.add(10.0f);
        operandi.add(3.0f);
        operandi.add(2.0f);
        assertEquals(5.0f, calcolatrice.sottrazionePiuOperandi(operandi), 0.001);
    }

    @Test
    public void testControlloToken() throws RemoteException {
        ImplCalcolatrice calcolatrice = new ImplCalcolatrice();
        assertTrue(calcolatrice.controlloToken("a1159e9df3670d549d04524532629f5477ceb7deec9b45e47e8c009506ecb2c8"));
        assertFalse(calcolatrice.controlloToken("wrong_pwd"));
    }

    @Test
    public void testControlloAutorizzazione() throws RemoteException {
        ImplCalcolatrice calcolatrice = new ImplCalcolatrice();
        assertTrue(calcolatrice.controlloAutorizzazione("a1159e9df3670d549d04524532629f5477ceb7deec9b45e47e8c009506ecb2c8"));
        assertFalse(calcolatrice.controlloAutorizzazione("wrong_pwd"));
    }
}