package com.example.demo.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fromEmail;
    private String toEmail;
    private int amount;
    private LocalDateTime timestamp;

    public Transaction() {}

    public Transaction(String fromEmail, String toEmail, int amount) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public int getId() { return id; }
    public String getFromEmail() { return fromEmail; }
    public String getToEmail() { return toEmail; }
    public int getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
