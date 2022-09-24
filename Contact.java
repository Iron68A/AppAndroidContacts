package fr.uha.contact;

import java.io.Serializable;


public class Contact implements Serializable  {
    
    private String NOM;
    private String PRENOM;
    private String    NUM;

    public Contact(String nom,String prenom, String numero){
        this.NOM=nom;
        this.NUM=numero;
        this.PRENOM=prenom;
    }

    @Override
    public String toString() {
        return (NOM+" "+ PRENOM +' '+ "Tél : "+NUM);
    }

    public String getNOM() {
        return NOM;
    }

    public String getNUM() {
        return NUM;
    }

    public String getPRENOM() {
        return PRENOM;
    }

    public void setNOM(String NOM) {
        this.NOM = NOM;
    }

    public void setPRENOM(String PRENOM) {
        this.PRENOM = PRENOM;
    }

    public void setNUM(String NUM) {
        this.NUM = NUM;
    }

    public int compareTo(Contact c){
        int comp = getNOM().compareTo(c.getNOM());
        if(comp ==0){
            comp =getPRENOM().compareTo(c.getPRENOM());

        }
        return comp;
    }

}
