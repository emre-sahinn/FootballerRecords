/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proje2;

/**
 *
 * @author ARX
 */
public class DoublyLinkedList {

    private TwoWayNode head;
    private TwoWayNode tail;

    class TwoWayNode {

        private Bilgi bilgiler;
        private TwoWayNode previous;
        private TwoWayNode next;

        public TwoWayNode() {
            previous = null;
            next = null;
        }

        public TwoWayNode(Bilgi bilgiler, TwoWayNode previous, TwoWayNode next) {
            this.bilgiler = new Bilgi(bilgiler);
            this.previous = previous;
            this.next = next;
        }

        public Bilgi getBilgiler() {
            return bilgiler;
        }

        public void setBilgiler(Bilgi bilgiler) {
            this.bilgiler = bilgiler;
        }

        public TwoWayNode getPrevious() {
            return previous;
        }

        public void setPrevious(TwoWayNode previous) {
            this.previous = previous;
        }

        public TwoWayNode getNext() {
            return next;
        }

        public void setNext(TwoWayNode next) {
            this.next = next;
        }
    }

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    public TwoWayNode getHead() {
        return head;
    }

    public void setHead(TwoWayNode head) {
        this.head = head;
    }

    public TwoWayNode getTail() {
        return tail;
    }

    public void setTail(TwoWayNode tail) {
        this.tail = tail;
    }

    //verilen String tipindeki ismi listeden siler
    public boolean delete(String isim) throws NullPointerException {
        if (isEmpty()) {
            return true;
        } else if (head.getBilgiler().getAdSoyad().equalsIgnoreCase(isim)) {
            deleteHeadNode();
        } else if (tail.getBilgiler().getAdSoyad().equalsIgnoreCase(isim)) {
            tail.previous.next = null;
            tail = tail.previous;
            System.out.println("Silme BASARISIZ! tail: " + tail.getBilgiler().getAdSoyad() + " data : " + isim);
        } else {
            TwoWayNode position = head.next;

            while (position != null && !position.getBilgiler().getAdSoyad().equalsIgnoreCase(isim)) {
                position = position.next;
            }
            try {
                position.next.previous = position.previous;
                position.previous.next = position.next;
            } catch (NullPointerException e) {
                return true;
            }
        }
        return false;
    }

    //Verilen String tipindeki ismi listede arar ve bulursa bilgilerini döndürür
    //bulamazsa null döndürür.
    public Bilgi find(String aranan) {
        TwoWayNode position = head;
        while (position != null) {
            if (position.getBilgiler().getAdSoyad().equalsIgnoreCase(aranan)) {
                return position.getBilgiler();
            }
            position = position.next;
        }
        return null;
    }

    //verilen nesneyi listede alfabetik sıraya göre ekler.
    public void add(Bilgi data) {
        if (data == null) return;
        if (isEmpty()) { //liste boş ise head ve tail aynı nesneyi işaret eder
            head = tail = new TwoWayNode(data, null, null);
        }
        else if (head.getBilgiler().getAdSoyad().compareToIgnoreCase(data.getAdSoyad()) > 0) 
        //gelen veri head den küçükse head in soluna ekle
        {
            TwoWayNode newNode = new TwoWayNode(data, null, head);
            head.previous = newNode;
            head = newNode;
        } else if (tail.getBilgiler().getAdSoyad().compareToIgnoreCase(data.getAdSoyad()) <= 0) 
        //gelen veri tail den buyukse tail in sağına ekle
        {
            tail.next = new TwoWayNode(data, tail, null);
            tail = tail.next;
        } else { //hiçbiri değilse..
            TwoWayNode position = head;
            //gelen veriden küçük isimli birini bulasıya kadar ilerle..
            TwoWayNode newNode = new TwoWayNode(data, null, null);
            while (position != null && position.getBilgiler().getAdSoyad().compareToIgnoreCase(newNode.getBilgiler().getAdSoyad()) < 0) {
                position = position.next;
            }
            //bulunca araya ekle
            newNode.previous = position.previous;
            position.previous.next = newNode;
            position.previous = newNode;
            newNode.next = position;
        }
    }

    //en baştaki elemanı yani head i siler
    public void deleteHeadNode() {
        if (!isEmpty()){
            if (head.next != null){
                head.next.previous = null;
                head = head.next;
            }
            else{
                clear();
            }
        }
    }

    //listenin uzunluğunu verir
    public int size() {
        if (isEmpty()) return 0;
        int count = 0;
        TwoWayNode position = head;
        while (position != null) {
            count++;
            position = position.next;
        }
        return count;
    }

    //listeyi yazdırır
    public void outputList() {
        TwoWayNode position = head;
        while (position != null) {
            System.out.println(position.bilgiler);
            position = position.next;
        }
    }

    //liste boş mu diye kontrol ediyor
    public boolean isEmpty() {
        return (head == null);
    }

    //listeyi siliyor
    public void clear() {
        head = null;
    }
}
