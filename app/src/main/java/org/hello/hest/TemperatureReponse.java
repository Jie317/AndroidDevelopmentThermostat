package org.hello.hest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jie on 06/10/2016.
 */
public class TemperatureReponse {




    private Map<String, Double> mapNomsTems = new HashMap<String, Double>();  // Map le nom de capteur et le temperature.
    //private String nomCapteur; // identifie la réponse
    private int erreurTotal = 0; // 0 si pas d'erreur
    private String errorMessage; // si erreur != 0
    //private double temp; // stocke la température du capteur

    // getters et setters

    public int getErreurTotal() {
        return erreurTotal;
    }

    public void addToErreurSum(int erreur) {
        erreurTotal += erreur;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void addMapNomTemp(String nom, double temp) { mapNomsTems.put(nom, temp); }

    public Map<String, Double> getMapNomsTems() {
        return mapNomsTems;
    }

//    public double getTemp() {
//        return temp;
//    }
//    public void setTemp(double temp) {
//        this.temp = temp;
//    }
//
//    public String getNomCapteur() {
//        return nomCapteur;
//    }
//    public void setNomCapteur(String nomCapteur) {
//        this.nomCapteur = nomCapteur;
//    }

}
