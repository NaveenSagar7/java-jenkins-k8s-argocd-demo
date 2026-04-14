package com.example;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws Exception {
        int port = 8081;

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new RootHandler());
        server.createContext("/health", new HealthHandler());
        server.createContext("/info", new InfoHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Server running on port " + port);
    }

    // ================= ROOT UI =================
    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String response =
                    "<html>" +
                    "<head>" +
                    "<title>Football Live</title>" +

                    "<style>" +
                    "body { margin:0; font-family:Segoe UI; background:linear-gradient(135deg,#0f172a,#1e293b); color:white; }" +

                    ".header { text-align:center; padding:30px 20px; background:#16a34a; font-size:28px; font-weight:bold; }" +

                    ".subtext { text-align:center; font-size:16px; color:#cbd5f5; margin-top:10px; }" +

                    ".section { text-align:center; margin-top:30px; font-size:18px; color:#94a3b8; }" +

                    ".container { padding:20px; max-width:600px; margin:auto; }" +

                    ".card { background:#1e293b; padding:20px; margin-bottom:15px; border-radius:12px; " +
                    "box-shadow:0 4px 15px rgba(0,0,0,0.5); transition:0.3s; }" +

                    ".card:hover { transform:scale(1.03); }" +

                    ".match { font-size:18px; font-weight:bold; margin-bottom:8px; }" +

                    ".live { color:#22c55e; font-weight:bold; }" +

                    ".footer { text-align:center; margin-top:40px; font-size:14px; color:#94a3b8; }" +
                    "</style>" +

                    "</head>" +

                    "<body>" +

                    "<div class='header'>Football Live</div>" +

                    "<div class='subtext'>Welcome to Football Live where you do not miss any update</div>" +

                    "<div class='section'>Information of all matches happening today</div>" +

                    "<div class='container'>" +

                    "<div class='card'>" +
                    "<div class='match'>Real Madrid vs Barcelona</div>" +
                    "<div class='live'>Live</div>" +
                    "</div>" +

                    "<div class='card'>" +
                    "<div class='match'>Arsenal vs Man City</div>" +
                    "<div class='live'>Live</div>" +
                    "</div>" +

                    "<div class='card'>" +
                    "<div class='match'>Man United vs Chelsea</div>" +
                    "<div class='live'>Live</div>" +
                    "</div>" +

                    "<div class='card'>" +
                    "<div class='match'>Bayern vs Dortmund</div>" +
                    "<div class='live'>Starting Soon</div>" +
                    "</div>" +

                    "</div>" +

                    "<div class='footer'>" +
                    "Developed by Naveen and Team - A DevOps Community" +
                    "</div>" +

                    "</body>" +
                    "</html>";

            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    // ================= HEALTH =================
    static class HealthHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "{ \"status\": \"UP\" }";
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        }
    }

    // ================= INFO =================
    static class InfoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "{ \"app\": \"football-live\", \"version\": \"1.0\" }";
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        }
    }
}
