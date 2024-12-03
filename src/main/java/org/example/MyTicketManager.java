package org.example.hm4;

public class MyTicketManager implements TicketManager {

    private Node headPension;
    private Node tailPension;
    private Node headOthers;
    private Node tailOthers;

    private int sizePension;
    private int sizeOthers;

    public int sizeAll() {
        return sizePension + sizeOthers;
    }
    public int sizePension() {
        return sizePension;
    }
    public int sizeOthers() {
        return sizeOthers;
    }
    @Override
    public String toString() {
        Node node = null;
        String str = "";
        if(headPension != null){
            node = headPension;
        } else if (headOthers != null){
            node = headOthers;
        }
        else return str;
        str += node.getData().getType()+"(" + node.getData().getId() +")" + "_" + node.getData().getRegisterTime() + "\n";
        while(node.getNext() != null){
            str += node.getNext().getData().getType()+"(" + node.getNext().getData().getId() +")" + "_" + node.getNext().getData().getRegisterTime() + "\n";
            node = node.getNext();
        }

        return str;
    }

    @Override
    public void add(Ticket ticket) {
        Node node = new Node(ticket);
        // Для пенсионеров 4 случая
        if(ticket.type.equals("pension")){
            sizePension++;
            // Если нет никого, то занимаем первое место
            if (this.tailPension == null && this.headOthers == null){
                this.headPension = node;
                this.tailPension = this.headPension;
            }
            // Если есть только пенсионеры, то занимаем хвост(встаем в конец)
            else if (this.tailPension != null && this.headOthers == null){
                this.tailPension.setNext(node);
                this.tailPension = node;
            }
            // Если пенсионеров нет, а остальные есть, то сдвигаем очередь остальных, ставновясь первым
            else if (this.tailPension == null && this.headOthers != null){
                // следующим для первого в очереди остальных ставновится вновь пришедший пенсионер
                // и этот пенсионер занимает место первого
                this.headPension = node;
                this.tailPension = this.headPension;
                this.headPension.setNext(this.headOthers);

            }
            // Если в очереди есть и пенсинеры и остальные
            else if (this.tailPension != null && this.headOthers != null){
                // то он встает в хвост пенсионеров
                this.tailPension.setNext(node);
                this.tailPension = node;
                // но становится следующим для начала очереди остальных
                this.tailPension.setNext(this.headOthers);
            }
        }
        // Для остальных 4 случая
        else{
            sizeOthers++;
            // Если нет никого, то занимаем первое место
            if (this.tailPension == null && this.tailOthers == null){
                this.headOthers = node;
                this.tailOthers = this.headOthers;
            }
            // Если пенсинеров нет, но есть остальные, то встаем в хвост остальных
            else if (this.tailPension == null && this.tailOthers != null) {
                this.tailOthers.setNext(node);
                this.tailOthers = node;
            }
            // пенсионеры есть в очереди, а остальных нет, то занимаем место
            // в конце всех пенсионеров
            else if(this.tailPension != null && this.tailOthers == null){
                this.tailPension.setNext(node);
                this.headOthers = node;
                this.tailOthers = this.headOthers;
            }
            // Если пенсионеры есть и остальные есть то встаем в хвост
            else if (this.tailPension != null && this.tailOthers != null) {
                this.tailOthers.setNext(node);
                this.tailOthers = node;
            }
        }
    }

    @Override
    public Ticket next() {
        Node node = new Node(null);
        if(headPension != null){
            node = headPension;
            // Если полученная нода не конец очереди, передвигаю начало на одного
            if(!node.equals(tailPension)) {
                headPension = node.getNext();
            }
            else {
                headPension = null;
            }
        } else if (headOthers != null){
            node = headOthers;
            // Если полученная нода не конец очереди, передвигаю начало на одного
            if(!node.equals(tailOthers)) {
                headOthers = node.getNext();
            }
            else {
                headOthers = null;
            }
        }

        return node.getData();
    }

    class Node {

        private Node next;
        private Ticket value;


        Node(Ticket ticket) {
            value = ticket;
        }

        public Ticket getData() {
            return value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
