/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proje2;

import java.util.ArrayList;

/**
 *
 * @author ARX
 */
public class Bilgi {
    private String adSoyad;
    private Date birthDate;
    private ArrayList takimlar = new ArrayList();
    
    
    public Bilgi(){
        this(null, null, null);
    }
    
    public Bilgi(String adSoyad, Date birthDate, ArrayList takimlar){
        this.adSoyad = adSoyad;
        this.birthDate = new Date(birthDate);
        this.takimlar = new ArrayList(takimlar);
    }
    
    public Bilgi(Bilgi data){
        this(data.getAdSoyad(), data.getBirthDate(), data.getTakimlar());
    }


    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public ArrayList getTakimlar() {
        return takimlar;
    }

    public void setTakimlar(ArrayList takimlar) {
        this.takimlar = takimlar;
    }

    @Override
    public String toString() {
        return "Bilgi{" + "adSoyad=" + adSoyad + ", birthDate=" + birthDate + ", takimlar=" + takimlar + '}';
    }
}
