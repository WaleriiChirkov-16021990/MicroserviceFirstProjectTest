package ru.chirkov.issueServiceApp.util.response;

public record ResponseByException(String message, long timestamp, String entityObjectName) {
}
