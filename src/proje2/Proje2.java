/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proje2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Proje2 {
    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream("sporcu.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Dosya bulunaadı.");
            System.exit(0);
        }
        DoublyLinkedList liste = new DoublyLinkedList();
        StringTokenizer veriler, tarih;
        while (scanner.hasNext()) {
            veriler = new StringTokenizer(scanner.nextLine(), ",");
            while (veriler.hasMoreTokens()) {
                String isim = veriler.nextToken();
                tarih = new StringTokenizer(veriler.nextToken().trim(), "/");
                Date dogumTarihi = new Date(Integer.parseInt(tarih.nextToken()), Integer.parseInt(tarih.nextToken()), Integer.parseInt(tarih.nextToken()));
                ArrayList takim = new ArrayList();
                while (veriler.hasMoreTokens()) {
                    takim.add(veriler.nextToken());
                }
                liste.add(new Bilgi(isim, dogumTarihi, takim));
            }
        }
        scanner.close();
        new GUI("Sporcu Arayüzü", liste);
    }
}
