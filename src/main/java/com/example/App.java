package com.example;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_HTML = "text/html; charset=UTF-8";
    private static final String CONTENT_TYPE_JSON = "application/json";

    public static void main(String[] args) throws Exception {
        int port = 8081;

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new RootHandler());
        server.createContext("/health", new HealthHandler());
        server.createContext("/info", new InfoHandler());

        server.setExecutor(null);
        server.start();

        LOGGER.info(() -> String.format("Server running on port %d", port));
    }

    // ================= ROOT =================
    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String response =
                    "<html>" +
                    "<head>" +
                    "<title>Football Live</title>" +
                    "<meta name='viewport' content='width=device-width, initial-scale=1'>" +

                    "<style>" +
                    "body { margin:0; font-family:'Segoe UI',sans-serif; background:#020617; color:white; }" +

                    ".header { text-align:center; padding:20px; font-size:28px; font-weight:bold;" +
                    " background:linear-gradient(90deg,#22c55e,#4ade80); }" +

                    ".layout { display:flex; gap:20px; padding:20px; }" +

                    ".left, .right {" +
                    " width:20%;" +
                    " background:rgba(255,255,255,0.05);" +
                    " padding:15px;" +
                    " border-radius:12px;" +
                    " height:80vh;" +
                    " overflow:auto;" +
                    "}" +

                    ".center { width:60%; }" +

                    ".card {" +
                    " background:rgba(255,255,255,0.05);" +
                    " border-radius:16px;" +
                    " padding:20px;" +
                    " margin-bottom:20px;" +
                    " box-shadow:0 8px 25px rgba(0,0,0,0.6);" +
                    "}" +

                    ".teams { font-size:18px; font-weight:bold; }" +
                    ".score { font-size:26px; margin:10px 0; }" +

                    ".status { padding:5px 12px; border-radius:20px; font-size:12px; }" +
                    ".live { background:#22c55e; color:black; }" +
                    ".delay { background:#f59e0b; color:black; }" +
                    ".soon { background:#3b82f6; }" +

                    ".panel-title { font-weight:bold; margin-bottom:10px; }" +
                    ".item { margin-bottom:8px; font-size:14px; }" +

                    /* NEWS STYLES */
                    ".news-card {" +
                    " background:rgba(255,255,255,0.06);" +
                    " padding:12px;" +
                    " border-radius:10px;" +
                    " margin-bottom:12px;" +
                    " transition:0.3s;" +
                    "}" +

                    ".news-card:hover {" +
                    " background:rgba(255,255,255,0.12);" +
                    " transform:translateY(-2px);" +
                    "}" +

                    ".news-title {" +
                    " font-size:14px;" +
                    " font-weight:bold;" +
                    " margin-bottom:6px;" +
                    "}" +

                    ".news-desc {" +
                    " font-size:12px;" +
                    " color:#cbd5f5;" +
                    " margin-bottom:6px;" +
                    "}" +

                    ".read-more {" +
                    " font-size:11px;" +
                    " color:#22c55e;" +
                    " cursor:default;" +
                    "}" +

                    "@media(max-width:900px) {" +
                    " .layout { flex-direction:column; }" +
                    " .left, .right, .center { width:100%; }" +
                    "}" +

                    "</style>" +
                    "</head>" +

                    "<body>" +

                    "<div class='header'>⚽ Football Live Dashboard - ARGOCD !</div>" +

                    "<div class='layout'>" +

                    // LEFT PANEL
                    "<div class='left'>" +
                    "<div class='panel-title'>Champions League Teams</div>" +
                    "<div class='item'>Real Madrid</div>" +
                    "<div class='item'>Barcelona</div>" +
                    "<div class='item'>Bayern Munich</div>" +
                    "<div class='item'>PSG</div>" +
                    "<div class='item'>Man City</div>" +
                    "<div class='item'>Arsenal</div>" +
                    "<div class='item'>Inter Milan</div>" +
                    "<div class='item'>AC Milan</div>" +
                    "<div class='item'>Dortmund</div>" +
                    "<div class='item'>Napoli</div>" +
                    "</div>" +

                    // CENTER
                    "<div class='center' id='matches'></div>" +

                    // RIGHT PANEL (NEWS)
                    "<div class='right'>" +
                    "<div class='panel-title'>Latest Football News</div>" +

                    "<div class='news-card'>" +
                    "<div class='news-title'>🌍 FIFA World Cup 2026 approaching</div>" +
                    "<div class='news-desc'>Preparations intensify as teams gear up for June 8 kickoff.</div>" +
                    "<div class='read-more'>Read more →</div>" +
                    "</div>" +

                    "<div class='news-card'>" +
                    "<div class='news-title'>🔥 Mbappe unstoppable this season</div>" +
                    "<div class='news-desc'>Goal scoring form making him the most dangerous forward.</div>" +
                    "<div class='read-more'>Read more →</div>" +
                    "</div>" +

                    "<div class='news-card'>" +
                    "<div class='news-title'>🚑 Ekitike injury setback</div>" +
                    "<div class='news-desc'>Young striker ruled out for months after serious injury.</div>" +
                    "<div class='read-more'>Read more →</div>" +
                    "</div>" +

                    "<div class='news-card'>" +
                    "<div class='news-title'>⚪ Madrid rebuild incoming?</div>" +
                    "<div class='news-desc'>Big tactical and squad changes expected next season.</div>" +
                    "<div class='read-more'>Read more →</div>" +
                    "</div>" +

                    "<div class='news-card'>" +
                    "<div class='news-title'>👔 Next Madrid manager?</div>" +
                    "<div class='news-desc'>Club exploring options for future leadership.</div>" +
                    "<div class='read-more'>Read more →</div>" +
                    "</div>" +

                    "</div>" +

                    "</div>" +

                    "<script>" +
                    "let matches = [" +
                    "{teams:'Real Madrid vs Barcelona', status:'delay', score:'-- : --'}," +
                    "{teams:'Arsenal vs Man City', status:'live', score:'2 : 1'}," +
                    "{teams:'Man United vs Liverpool', status:'live', score:'30 MIN TO GO'}," +
                    "{teams:'Bayern vs Dortmund', status:'soon', score:'-- : --'}" +
                    "];" +

                    "function render() {" +
                    "let html='';" +
                    "matches.forEach(m => {" +
                    "html += `<div class='card'>" +
                    "<div class='teams'>${m.teams}</div>" +
                    "<div class='score'>${m.score}</div>" +
                    "<div class='status ${m.status}'>" +
                    "${m.status==='live'?'LIVE':m.status==='delay'?'DELAYED':'STARTING SOON'}" +
                    "</div>" +
                    "</div>`;" +
                    "});" +
                    "document.getElementById('matches').innerHTML = html;" +
                    "}" +

                    "setInterval(render,5000);" +
                    "render();" +
                    "</script>" +

                    "</body>" +
                    "</html>";

            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().add(HEADER_CONTENT_TYPE, CONTENT_TYPE_HTML);
            exchange.sendResponseHeaders(200, bytes.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    // ================= HEALTH =================
    static class HealthHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "{ \"status\": \"UP\" }";
            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().add(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
            exchange.sendResponseHeaders(200, bytes.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    // ================= INFO =================
    static class InfoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "{ \"app\": \"football-live\", \"version\": \"2.0\", \"env\": \"dev\" }";
            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().add(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
            exchange.sendResponseHeaders(200, bytes.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }
}