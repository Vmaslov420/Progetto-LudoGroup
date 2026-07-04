<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css">
</head>
<body class="login-body">

<div class="login-container">
    <h1>Accedi</h1>

   
    <% String messaggio = request.getParameter("messaggio"); %>

    
    <% if ("registrazione_ok".equals(messaggio)) { %>
    <div class="messaggio-ok">Registrazione completata con successo. Ora puoi accedere.</div>
    <% } %>

    
    <% if ("logout_ok".equals(messaggio)) { %>
    <div class="messaggio-ok">Logout effettuato correttamente.</div>
    <% } %>

  
    <% if (request.getAttribute("errore") != null) { %>
    <div class="errore-server">${errore}</div>
    <% } %>

    
    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="login-campo">
            <label for="email">Email</label>
            <input type="email" id="email" name="email"
                   placeholder="Inserisci la tua email"
                   autocomplete="email" required>
        </div>

        <div class="login-campo">
            <label for="password">Password</label>
            <input type="password" id="password" name="password"
                   placeholder="Inserisci la tua password"
                   autocomplete="current-password" required>
        </div>

        <button type="submit" class="btn-submit">Accedi</button>
    </form>

    <div class="link-reg">
        Non hai un account?
        <a href="${pageContext.request.contextPath}/registrazione">Registrati</a>
    </div>
</div>

</body>
</html>