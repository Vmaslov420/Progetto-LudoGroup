<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dettaglio prodotto</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 30px;
            background-color: #f7f7f7;
        }

        .contenitore {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            border: 1px solid #ccc;
            border-radius: 8px;
            padding: 25px;
        }

        h1 {
            margin-bottom: 20px;
        }

        p {
            margin: 10px 0;
            font-size: 16px;
        }

        .prezzo {
            font-size: 22px;
            font-weight: bold;
            color: #1a7f37;
        }

        .azioni {
            margin-top: 25px;
            display: flex;
            gap: 10px;
        }

        .azioni a {
            display: inline-block;
            padding: 10px 16px;
            text-decoration: none;
            border-radius: 6px;
            border: 1px solid #333;
            background: #f5f5f5;
            color: #000;
        }

        .azioni a:hover {
            background: #e9e9e9;
        }

        .disponibile {
            color: #1a7f37;
            font-weight: bold;
        }

        .non-disponibile {
            color: #b42318;
            font-weight: bold;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/fragments/header.jsp" />

<div class="contenitore">

    <%
        String messaggioConferma = (String) session.getAttribute("messaggioConferma");

        /*
         * Dopo aver letto il messaggio, lo rimuoviamo dalla sessione
         * così viene mostrato una sola volta.
         */
        if (messaggioConferma != null) {
            session.removeAttribute("messaggioConferma");
        }
    %>

    <%
        if (messaggioConferma != null) {
    %>
    <div style="background: #e8f7e8; color: #1f6b1f; border: 1px solid #b7dfb7; padding: 12px; border-radius: 6px; margin-bottom: 20px;">
        <%= messaggioConferma %>
    </div>
    <%
        }
    %>

    <h1>${prodotto.nome}</h1>

    <p>
        <strong>Categoria:</strong>
        <c:choose>
            <c:when test="${not empty prodotto.nomeCategoria}">
                ${prodotto.nomeCategoria}
            </c:when>
            <c:otherwise>
                -
            </c:otherwise>
        </c:choose>
    </p>

    <p><strong>Descrizione:</strong> ${prodotto.descrizione}</p>
    <p class="prezzo">€ ${prodotto.prezzo}</p>
    <p><strong>IVA:</strong> ${prodotto.iva}%</p>

    <p>
        <strong>Disponibilità:</strong>
        <c:choose>
            <c:when test="${prodotto.stock > 0}">
                <span class="disponibile">Disponibile (${prodotto.stock} pezzi)</span>
            </c:when>
            <c:otherwise>
                <span class="non-disponibile">Non disponibile</span>
            </c:otherwise>
        </c:choose>
    </p>

    <div class="azioni">

        <!--
            Form POST per aggiungere il prodotto al carrello.
            Passiamo l'id del prodotto tramite un campo hidden.
        -->
        <form action="${pageContext.request.contextPath}/carrello/aggiungi" method="post" style="display: inline;">
            <input type="hidden" name="id" value="${prodotto.id}">
            <button type="submit" style="padding: 10px 16px; border-radius: 6px; border: 1px solid #2563eb; background: #2563eb; color: white; cursor: pointer;">
                Aggiungi al carrello
            </button>
        </form>

        <a href="${pageContext.request.contextPath}/catalogo">Torna al catalogo</a>
    </div>
</div>

</body>
</html>