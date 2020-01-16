/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proje2;

import java.util.Scanner;

/**
 *
 * @author x
 */
public class Date {

    private int month;
    private int day;
    private int year;

    public Date() {
        month = 1;
        day = 1;
        year = 1000;
    }

    public Date(int day, int monthInt, int year) {
        setDate(day, monthInt, year);
    }

    public Date(int year) {
        setDate(1, 1, year);
    }

    public Date(Date aDate) {
        if (aDate == null) //geçersiz bir tarihse...
        {
            System.out.println("Fatal Error.");
            System.exit(0);
        }

        month = aDate.month;
        day = aDate.day;
        year = aDate.year;
    }

    public boolean setDate(int day, int monthInt, int year) {
        if (dateOK(day, monthInt, year)) {
            this.month = monthInt;
            this.day = day;
            this.year = year;
            return true;
        } else {
            System.out.println("Dogum tarihi hatalı!");
            return false;
        }
    }

    @Override
    public String toString() {
        String formatttedDay = String.valueOf(day);
        String formatttedMonth = String.valueOf(month);
        if(day<10){
            formatttedDay = "0" + formatttedDay; //ondan küçük sayıların başına sıfır konulmasını sağlar (09, 08 vs.)
        }
        if(month<10){
            formatttedMonth = "0" + formatttedMonth;
        }
        return formatttedDay + "/" + formatttedMonth + "/" + year;
    }

    private boolean dateOK(int dayInt, int monthInt, int yearInt) { //geçerli bir tarih olup olmadığını kontrol eder
        return ((monthInt >= 1) && (monthInt <= 12)
                && (dayInt >= 1) && (dayInt <= 31)
                && (yearInt >= 1000) && (yearInt <= 9999));
    }
}
