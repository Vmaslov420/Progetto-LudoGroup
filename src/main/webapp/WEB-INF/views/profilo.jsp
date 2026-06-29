<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.bean.Utente" %>

<%
    Utente utente = (Utente) request.getAttribute("utenteProfilo");
    //passo i dati dalla servlet alla jsp (è piu coerente con MVC)
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profilo Utente</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f5f5;
            margin: 0;
            padding: 0;
        }

        .contenitore {
            max-width: 800px;
            margin: 30px auto;
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 12px rgba(0,0,0,0.08);
        }

        h1 {
            margin-bottom: 20px;
            color: #333;
        }

        .riga {
            margin-bottom: 14px;
            font-size: 16px;
            color: #444;
        }

        .etichetta {
            font-weight: bold;
            color: #222;
        }
    </style>
</head>
<body>


<!-- Include del fragment header.jsp -->
<!-- In questo modo riutilizziamo lo stesso header in più pagine -->
<jsp:include page="/WEB-INF/views/fragments/header.jsp" />

<div class="contenitore">
    <h1>Il tuo profilo</h1>

    <!-- Stampa dei dati dell'utente -->
    <!-- maggiore% è una expression JSP: serve a stampare il valore restituito dal metodo -->

    <div class="riga">
        <span class="etichetta">Nome:</span>
        <%= utente.getNome() %>
    </div>

    <div class="riga">
        <span class="etichetta">Cognome:</span>
        <%= utente.getCognome() %>
    </div>

    <div class="riga">
        <span class="etichetta">Email:</span>
        <%= utente.getEmail() %>
    </div>

    <div class="riga">
        <span class="etichetta">Nickname:</span>
        <%= utente.getNickname() %>
    </div>

    <div class="riga">
        <span class="etichetta">Telefono:</span>
        <%= utente.getTelefono() %>
    </div>

    <div class="riga">
        <span class="etichetta">Ruolo:</span>
        <%= utente.getRuolo() %>
    </div>
</div>

</body>
</html>