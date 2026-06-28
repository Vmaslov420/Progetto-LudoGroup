<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }

        body {
            font-family: Arial, sans-serif;
            background: #f5f5f5;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            padding: 20px;
        }

        .form-container {
            background: white;
            padding: 35px;
            border-radius: 8px;
            box-shadow: 0 2px 12px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 420px;
        }

        h1 {
            font-size: 24px;
            margin-bottom: 24px;
            color: #333;
        }

        .campo {
            margin-bottom: 18px;
        }

        label {
            display: block;
            font-size: 14px;
            font-weight: bold;
            color: #444;
            margin-bottom: 5px;
        }

        input {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 15px;
        }

        input:focus {
            outline: none;
            border-color: #007bff;
        }

        .messaggio-ok {
            background: #e8f5e9;
            color: #2e7d32;
            border: 1px solid #a5d6a7;
            border-radius: 5px;
            padding: 10px 14px;
            margin-bottom: 16px;
            font-size: 14px;
        }

        .errore-server {
            background: #fdecea;
            color: #c62828;
            border: 1px solid #ef9a9a;
            border-radius: 5px;
            padding: 10px 14px;
            margin-bottom: 16px;
            font-size: 14px;
        }

        button[type="submit"] {
            width: 100%;
            padding: 12px;
            background: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
        }

        button[type="submit"]:hover {
            background: #0056b3;
        }

        .link-reg {
            text-align: center;
            margin-top: 16px;
            font-size: 14px;
            color: #666;
        }

        .link-reg a {
            color: #007bff;
            text-decoration: none;
        }
    </style>
</head>
<body>

<div class="form-container">
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
        <div class="campo">
            <label for="email">Email</label>
            <input type="email" id="email" name="email"
                   placeholder="Inserisci la tua email"
                   autocomplete="email" required>
        </div>

        <div class="campo">
            <label for="password">Password</label>
            <input type="password" id="password" name="password"
                   placeholder="Inserisci la tua password"
                   autocomplete="current-password" required>
        </div>

        <button type="submit">Accedi</button>
    </form>

    <div class="link-reg">
        Non hai un account?
        <a href="${pageContext.request.contextPath}/registrazione">Registrati</a>
    </div>
</div>

</body>
</html>