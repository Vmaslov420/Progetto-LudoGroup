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

<!-- Collegamento del foglio di stile in css -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css">

<header class="main-header">
    <div class="header-container">

        <!-- Logo / nome sito -->
		 <!-- pageContext.request.contextPath serve a costruire URL corretti in base al nome del progetto deployato su Tomcat -->
        <a href="${pageContext.request.contextPath}/" class="logo-site">
           TechHeaven
        </a>

        <!-- Menu di navigazione -->
        <nav class="nav-menu">

            <!-- Link sempre visibile -->
            <a href="${pageContext.request.contextPath}/" class="nav-link">
               Home
            </a>
			<!-- se esiste un utente in sessione significa che il login è stato effettuato -->
            <% if (utenteLoggato != null) { %>

                <!-- Link visibili solo all'utente autenticato -->
                <a href="${pageContext.request.contextPath}/profilo" class="nav-link">
                   Profilo
                </a>

                <!-- Messaggio di benvenuto con nickname dell'utente-->
                <span class="welcome-msg">
                    Ciao, <%= utenteLoggato.getNickname() %>
                </span>

                <!-- Link di logout -->
                <a href="${pageContext.request.contextPath}/logout" class="nav-link-logout">
                   Logout
                </a>

            <% } else { %>
				<!-- Se non esiste nessun utente in sessione, mostro i link per autenticarsi o registrarsi -->
                <!-- Link visibili solo a chi NON è autenticato -->
                <a href="${pageContext.request.contextPath}/login" class="nav-link-primary">
                   Login
                </a>

                <a href="${pageContext.request.contextPath}/registrazione" class="nav-link-primary">
                   Registrati
                </a>

            <% } %>

        </nav>
    </div>
</header>