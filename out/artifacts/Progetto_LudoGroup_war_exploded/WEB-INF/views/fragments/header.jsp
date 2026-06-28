<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.bean.Utente" %>

<%
    // Recupera dalla sessione l'attributo "utente"
    // Questo attributo viene salvato nel LoginServlet dopo il login riuscito:
    // session.setAttribute("utente", utente);
    //
    // Il cast (Utente) serve perché getAttribute() restituisce Object.
    Utente utenteLoggato = (Utente) session.getAttribute("utente");
%>

<header style="padding: 15px 30px; background: #ffffff; border-bottom: 1px solid #ddd;">

    <div style="display: flex; justify-content: space-between; align-items: center;">

        <!-- Logo / nome sito -->
        <!-- pageContext.request.contextPath serve a costruire URL corretti
             in base al nome del progetto deployato su Tomcat -->
        <a href="${pageContext.request.contextPath}/"
           style="text-decoration: none; color: #333; font-size: 20px; font-weight: bold;">
            TechHeaven
        </a>

        <!-- Menu di navigazione -->
        <nav style="display: flex; gap: 15px; align-items: center;">

            <!-- Link sempre visibile -->
            <a href="${pageContext.request.contextPath}/"
               style="text-decoration: none; color: #333;">
                Home
            </a>

            <%
                // Se esiste un utente in sessione, significa che il login è stato effettuato
                if (utenteLoggato != null) {
            %>

            <!-- Link visibili solo all'utente autenticato -->
            <a href="${pageContext.request.contextPath}/profilo"
               style="text-decoration: none; color: #333;">
                Profilo
            </a>

            <!-- Messaggio di benvenuto con nickname dell'utente -->
            <span style="color: #333;">
                    Ciao, <%= utenteLoggato.getNickname() %>
                </span>

            <!-- Link di logout -->
            <a href="${pageContext.request.contextPath}/logout"
               style="text-decoration: none; color: #c62828;">
                Logout
            </a>

            <%
            } else {
                // Se non esiste nessun utente in sessione,
                // mostro i link per autenticarsi o registrarsi
            %>

            <!-- Link visibili solo a chi NON è autenticato -->
            <a href="${pageContext.request.contextPath}/login"
               style="text-decoration: none; color: #007bff;">
                Login
            </a>

            <a href="${pageContext.request.contextPath}/registrazione"
               style="text-decoration: none; color: #007bff;">
                Registrati
            </a>

            <%
                }
            %>

        </nav>
    </div>
</header>