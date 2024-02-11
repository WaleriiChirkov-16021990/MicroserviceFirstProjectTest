package ru.chirkov.readerServiceApp.util.response;

public record ResponseByException(String message, long timestamp, String entityObjectName) {
}
