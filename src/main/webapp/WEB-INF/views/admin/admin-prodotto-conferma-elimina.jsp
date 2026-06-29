<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.bean.Prodotto" %>

<%
  /*
   * Recuperiamo il prodotto passato dalla servlet.
   * Servirà per mostrare il nome nella pagina di conferma.
   */
  Prodotto prodotto = (Prodotto) request.getAttribute("prodotto");
%>

<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Conferma eliminazione</title>

  <style>
    body {
      font-family: Arial, sans-serif;
      background: #f4f6f8;
      margin: 0;
      padding: 0;
    }

    .contenitore {
      max-width: 600px;
      margin: 80px auto;
      background: #fff;
      padding: 40px;
      border-radius: 10px;
      box-shadow: 0 2px 12px rgba(0,0,0,0.08);
      text-align: center;
    }

    .icona-avviso {
      font-size: 48px;
      margin-bottom: 16px;
    }

    h1 {
      color: #c62828;
      margin-bottom: 12px;
    }

    .messaggio {
      color: #555;
      margin-bottom: 30px;
      line-height: 1.6;
    }

    .nome-prodotto {
      font-weight: bold;
      color: #222;
    }

    .avviso-soft-delete {
      font-size: 13px;
      color: #888;
      margin-bottom: 30px;
    }

    .azioni {
      display: flex;
      justify-content: center;
      gap: 14px;
    }

    .bottone {
      padding: 10px 20px;
      border-radius: 6px;
      font-weight: bold;
      border: none;
      cursor: pointer;
      font-size: 14px;
      text-decoration: none;
      display: inline-block;
    }

    .bottone-elimina {
      background: #c62828;
      color: white;
    }

    .bottone-elimina:hover {
      background: #a71c1c;
    }

    .bottone-annulla {
      background: #6c757d;
      color: white;
    }

    .bottone-annulla:hover {
      background: #5a6268;
    }
  </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/fragments/header.jsp" />

<div class="contenitore">

  <div class="icona-avviso">⚠️</div>

  <h1>Conferma eliminazione</h1>

  <p class="messaggio">
    Stai per eliminare il prodotto:<br>
    <%
      /*
       * Mostriamo il nome del prodotto così l'admin sa esattamente
       * cosa sta eliminando prima di confermare.
       */
      if (prodotto != null) {
    %>
    <span class="nome-prodotto">"<%= prodotto.getNome() %>"</span>
    <%
      }
    %>
  </p>

  <p class="avviso-soft-delete">
    Il prodotto verrà disattivato e non sarà più visibile nel catalogo.<br>
    Non verrà eliminato permanentemente dal database.
  </p>

  <!--
      Questo form invia un POST alla servlet di eliminazione.

      Perché usiamo un form POST e non un link?
      Perché l'eliminazione modifica dati.
      Un link sarebbe un GET, che non è semanticamente corretto
      per un'operazione che modifica lo stato del server.
  -->
  <form action="${pageContext.request.contextPath}/admin/prodotti/elimina" method="post">

    <%
      /*
       * Campo hidden per passare l'id del prodotto al POST.
       * L'utente non lo vede, ma la servlet lo riceve.
       */
      if (prodotto != null) {
    %>
    <input type="hidden" name="id" value="<%= prodotto.getId() %>">
    <%
      }
    %>

    <div class="azioni">
      <button type="submit" class="bottone bottone-elimina">
        Sì, elimina
      </button>

      <a href="${pageContext.request.contextPath}/admin/prodotti"
         class="bottone bottone-annulla">
        Annulla
      </a>
    </div>
  </form>
</div>

</body>
</html>