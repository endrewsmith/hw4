package org.example;

import java.time.OffsetDateTime;

/**
 * Можно изменять по своему усмотрению, не нарушая правила приоритезации очереди
 */
public class Ticket {

    private static int idSeq;

    /**
     * Автогенерация id
     */
    int id = ++idSeq;

    /**
     * Приоритеты типов:
     * 1) pension
     * 2) любые другие
     */
    String type;

    /**
     * Приоритет для ранней регистрации
     */
    OffsetDateTime registerTime;

    public Ticket(String type) {
        this.type = type;
        registerTime = OffsetDateTime.now();
    }

    public String getType(){
        return type;
    }

    public OffsetDateTime getRegisterTime(){
        return registerTime;
    }

    public int getId(){
        return id;
    }
}
