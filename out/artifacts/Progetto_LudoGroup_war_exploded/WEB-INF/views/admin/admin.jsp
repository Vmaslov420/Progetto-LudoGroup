<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.bean.Utente" %>

<!-- Questa jsp mostra la pagina iniziale dell'are amministratore -->

<%
    // Recupera dalla request l'utente admin passato dalla servlet
    Utente admin = (Utente) request.getAttribute("adminLoggato");
%>



<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Area Admin</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f6f8;
            margin: 0;
            padding: 0;
        }

        .contenitore {
            max-width: 1000px;
            margin: 30px auto;
            background: #ffffff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 12px rgba(0,0,0,0.08);
        }

        h1 {
            margin-bottom: 10px;
            color: #222;
        }

        p {
            color: #444;
            line-height: 1.6;
        }

        .box-link {
            margin-top: 25px;
            display: grid;
            gap: 15px;
        }

        .link-admin {
            display: block;
            padding: 14px 18px;
            background: #f8f9fa;
            border: 1px solid #ddd;
            border-radius: 8px;
            text-decoration: none;
            color: #333;
            font-weight: bold;
        }

        .link-admin:hover {
            background: #eef3f7;
        }
    </style>
</head>
<body>

<!-- Include dell'header comune -->
<jsp:include page="/WEB-INF/views/fragments/header.jsp" />

<div class="contenitore">
    <h1>Area Amministratore</h1>

    <!-- Messaggio di benvenuto -->
    <p>
        Benvenuto
        <strong><%= admin.getNickname() %></strong>,
        hai effettuato l'accesso come amministratore.
    </p>

    <p>
        Da questa sezione potrai gestire le funzionalità riservate dell'applicazione.
    </p>

    <!-- Link placeholder alle future funzionalità admin -->
    <div class="box-link">
        <a class="link-admin" href="#">Gestione prodotti</a>
        <a class="link-admin" href="#">Visualizza ordini</a>
        <a class="link-admin" href="#">Filtra ordini per cliente</a>
        <a class="link-admin" href="#">Filtra ordini per data</a>
    </div>
</div>

</body>
</html>