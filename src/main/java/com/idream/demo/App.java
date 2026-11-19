package com.idream.demo;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class App {
    public static String greeting() { return "Welcome to iDream IT Solution!"; }

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", App::handle);
        server.start();
        System.out.println("Open http://localhost:" + port);
    }

    private static void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if (!path.equals("/")) {
            byte[] missing = "Not found".getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(404, missing.length);
            try (OutputStream out = exchange.getResponseBody()) { out.write(missing); }
            return;
        }
        byte[] body;
        try (InputStream in = App.class.getResourceAsStream("/static/index.html")) {
            if (in == null) throw new IOException("Missing index.html");
            body = in.readAllBytes();
        }
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, body.length);
        try (OutputStream out = exchange.getResponseBody()) { out.write(body); }
    }
}
