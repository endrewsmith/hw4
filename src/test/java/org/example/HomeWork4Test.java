package org.example;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeWork4Test {

    // Проверим вспомогательные методы size
    @Test
    void sizeTest() {
        MyTicketManager myTicketManager = new MyTicketManager();
        myTicketManager.add(new Ticket("pension"));
        myTicketManager.add(new Ticket("pension"));
        myTicketManager.add(new Ticket("pension"));

        System.out.println(myTicketManager);

        assertEquals(3, myTicketManager.sizePension());
        assertEquals(3, myTicketManager.sizeAll());
        assertEquals(0, myTicketManager.sizeOthers());

        myTicketManager.add(new Ticket("others"));
        myTicketManager.add(new Ticket("others"));
        myTicketManager.add(new Ticket("others"));

        System.out.println(myTicketManager);

        assertEquals(3, myTicketManager.sizePension());
        assertEquals(6, myTicketManager.sizeAll());
        assertEquals(3, myTicketManager.sizeOthers());

        for (int i = 0; i < 100; i++) {
            myTicketManager.add(new Ticket("pension"));
        }
        assertEquals(103, myTicketManager.sizePension());
    }

    // 1 кейс: добавляем только пенсионеров
    @Test
    void add_1_Test() throws InterruptedException {

        MyTicketManager myTicketManager_1 = new MyTicketManager();
        myTicketManager_1.add(new Ticket("pension"));
        TimeUnit.MILLISECONDS.sleep(1);
        myTicketManager_1.add(new Ticket("pension"));
        TimeUnit.MILLISECONDS.sleep(1);
        myTicketManager_1.add(new Ticket("pension"));
        // Проверим количество добавленных
        assertEquals(3, myTicketManager_1.sizePension());
        System.out.println(myTicketManager_1);

        // Заодно проверяем метод next
        Ticket ticket_1 = myTicketManager_1.next();
        Ticket ticket_2 = myTicketManager_1.next();
        Ticket ticket_3 = myTicketManager_1.next();

        // Проверим что добавились элементы с разными id
        assertTrue(ticket_1.getId() != ticket_2.getId()
                && ticket_2.getId() != ticket_3.getId()
                && ticket_1.getId() != ticket_3.getId());
        // Проверим иерархию по времени
        assertTrue(ticket_1.getRegisterTime().isBefore(ticket_2.getRegisterTime()));
        assertTrue(ticket_2.getRegisterTime().isBefore(ticket_3.getRegisterTime()));
        assertTrue(ticket_1.getRegisterTime().isBefore(ticket_3.getRegisterTime()));
    }

    // 2 кейс: добавляем только остальных
    @Test
    void add_2_Test() throws InterruptedException {

        MyTicketManager myTicketManager_2 = new MyTicketManager();
        myTicketManager_2.add(new Ticket("others"));
        TimeUnit.MILLISECONDS.sleep(1);
        myTicketManager_2.add(new Ticket("others"));
        // Проверим количество добавленных
        assertEquals(2, myTicketManager_2.sizeOthers());
        System.out.println(myTicketManager_2);

        // Заодно проверяем метод next
        Ticket ticket_1 = myTicketManager_2.next();
        Ticket ticket_2 = myTicketManager_2.next();

        // Проверим что добавились элементы с разными id
        assertTrue(ticket_1.getId() != ticket_2.getId());

        // Проверим иерархию по времени
        assertTrue(ticket_1.getRegisterTime().isBefore(ticket_2.getRegisterTime()));
    }

    // 3 кейс: добавляем пенсинеров потом остальных
    @Test
    void add_3_Test() {

        MyTicketManager myTicketManager_3 = new MyTicketManager();
        myTicketManager_3.add(new Ticket("pension"));
        myTicketManager_3.add(new Ticket("pension"));
        myTicketManager_3.add(new Ticket("others"));
        myTicketManager_3.add(new Ticket("others"));
        // Проверим количество добавленных
        assertEquals(2, myTicketManager_3.sizePension());
        assertEquals(2, myTicketManager_3.sizeOthers());

        // Проверяем последовательность, как положили так и достаем
        assertTrue(myTicketManager_3.next().getType().equals("pension"));
        assertTrue(myTicketManager_3.next().getType().equals("pension"));
        assertTrue(myTicketManager_3.next().getType().equals("others"));
        assertTrue(myTicketManager_3.next().getType().equals("others"));
        assertTrue(myTicketManager_3.next() == null);
    }

    // 4 кейс: добавляем остальных потом пенсионеров
    @Test
    void add_4_Test() throws InterruptedException {

        MyTicketManager myTicketManager_4 = new MyTicketManager();
        myTicketManager_4.add(new Ticket("others"));
        myTicketManager_4.add(new Ticket("others"));
        myTicketManager_4.add(new Ticket("pension"));
        myTicketManager_4.add(new Ticket("pension"));
        myTicketManager_4.add(new Ticket("pension"));

        System.out.println(myTicketManager_4);

        // Проверяем последовательность, сначала должны достать pension
        assertTrue(myTicketManager_4.next().getType().equals("pension"));
        assertTrue(myTicketManager_4.next().getType().equals("pension"));
        assertTrue(myTicketManager_4.next().getType().equals("pension"));
        assertTrue(myTicketManager_4.next().getType().equals("others"));
        assertTrue(myTicketManager_4.next().getType().equals("others"));
        assertTrue(myTicketManager_4.next() == null);
    }

    // 5 кейс: добавляем пенсинеров, остальных, а потом еще пенсинеров
    @Test
    void add_5_Test() {

        MyTicketManager myTicketManager_5 = new MyTicketManager();
        myTicketManager_5.add(new Ticket("pension"));
        myTicketManager_5.add(new Ticket("pension"));
        myTicketManager_5.add(new Ticket("others"));
        myTicketManager_5.add(new Ticket("others"));
        myTicketManager_5.add(new Ticket("pension"));
        myTicketManager_5.add(new Ticket("pension"));

        System.out.println(myTicketManager_5);

        // Проверяем последовательность, сначала должны достать pension
        assertTrue(myTicketManager_5.next().getType().equals("pension"));
        assertTrue(myTicketManager_5.next().getType().equals("pension"));
        assertTrue(myTicketManager_5.next().getType().equals("pension"));
        assertTrue(myTicketManager_5.next().getType().equals("pension"));
        assertTrue(myTicketManager_5.next().getType().equals("others"));
        assertTrue(myTicketManager_5.next().getType().equals("others"));
        assertTrue(myTicketManager_5.next() == null);
    }

    // 6 кейс: добавляем остальных, пенсинеров, а потом еще остальных
    @Test
    void add_6_Test() throws InterruptedException {

        MyTicketManager myTicketManager_6 = new MyTicketManager();
        myTicketManager_6.add(new Ticket("others"));
        myTicketManager_6.add(new Ticket("others"));
        myTicketManager_6.add(new Ticket("pension"));
        myTicketManager_6.add(new Ticket("pension"));
        myTicketManager_6.add(new Ticket("others"));
        myTicketManager_6.add(new Ticket("others"));

        System.out.println(myTicketManager_6);

        // Проверяем последовательность, сначала должны достать pension
        assertTrue(myTicketManager_6.next().getType().equals("pension"));
        assertTrue(myTicketManager_6.next().getType().equals("pension"));
        assertTrue(myTicketManager_6.next().getType().equals("others"));
        assertTrue(myTicketManager_6.next().getType().equals("others"));
        assertTrue(myTicketManager_6.next().getType().equals("others"));
        assertTrue(myTicketManager_6.next().getType().equals("others"));
        assertTrue(myTicketManager_6.next() == null);

    }
}
