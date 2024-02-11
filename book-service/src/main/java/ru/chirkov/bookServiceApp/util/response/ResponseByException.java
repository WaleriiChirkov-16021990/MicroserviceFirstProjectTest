package ru.chirkov.bookServiceApp.util.response;

public record ResponseByException(String message, long timestamp, String entityObjectName) {
}
