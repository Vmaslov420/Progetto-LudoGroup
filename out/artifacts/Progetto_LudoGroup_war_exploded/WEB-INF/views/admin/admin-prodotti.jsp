<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>  <!-- serve perchè usiamo una lista -->
<%@ page import="model.bean.Prodotto" %> <!-- serve perchè uso oggetti di tipo prodotto-->

<%
    /*
     * Recupera dalla request l'attributo "listaProdotti"
     * impostato nella servlet.
     *
     * Il cast è necessario perché getAttribute() restituisce Object. in java
     * devo esplicitamente dire che quell'oggetto è una List<Prodotto>
     */
    List<Prodotto> listaProdotti = (List<Prodotto>) request.getAttribute("listaProdotti");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestione Prodotti Admin</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f6f8;
            margin: 0;
            padding: 0;
        }

        .contenitore {
            max-width: 1200px;
            margin: 30px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 12px rgba(0,0,0,0.08);
        }

        h1 {
            margin-bottom: 20px;
            color: #222;
        }

        .azioni-top {
            margin-bottom: 20px;
        }

        .bottone {
            display: inline-block;
            padding: 10px 16px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-weight: bold;
        }

        .bottone:hover {
            background: #0056b3;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
            vertical-align: middle;
        }

        th {
            background: #f1f1f1;
            color: #333;
        }

        tr:nth-child(even) {
            background: #fafafa;
        }

        .azioni a {
            margin-right: 10px;
            text-decoration: none;
            font-weight: bold;
        }

        .modifica {
            color: #007bff;
        }

        .elimina {
            color: #c62828;
        }

        .badge-attivo {
            color: #2e7d32;
            font-weight: bold;
        }

        .badge-eliminato {
            color: #c62828;
            font-weight: bold;
        }

        .messaggio-vuoto {
            margin-top: 20px;
            color: #666;
            font-style: italic;
        }
    </style>
</head>
<body>

<!-- Include dell'header comune -->
<jsp:include page="/WEB-INF/views/fragments/header.jsp" />

<div class="contenitore">
    <h1>Gestione Prodotti</h1>

    <div class="azioni-top">
        <!-- Questo link porterà più avanti al form di inserimento prodotto -->
        <a class="bottone" href="${pageContext.request.contextPath}/admin/prodotti/nuovo">
            + Nuovo prodotto
        </a>
    </div>

    <%
        /*
         * Controllo difensivo: se la lista è null o vuota,
         * mostro un messaggio invece della tabella.
         */
        if (listaProdotti == null || listaProdotti.isEmpty()) {
    %>
    <p class="messaggio-vuoto">Nessun prodotto presente nel catalogo.</p>
    <%
    } else {
    %>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Prezzo</th>
            <th>IVA</th>
            <th>Stock</th>
            <th>Categoria</th>
            <th>Stato</th>
            <th>Azioni</th>
        </tr>
        </thead>
        <tbody>

        <%
            /*
             * Ciclo su tutti i prodotti della lista.
             *
             * La sintassi:
             * for (Tipo variabile : collezione)
             * è il for-each di Java.
             *
             * Si usa quando vuoi leggere tutti gli elementi di una collezione
             * in modo chiaro e compatto.
             */
            for (Prodotto prodotto : listaProdotti) {
        %>
        <tr>
            <td><%= prodotto.getId() %></td>

            <td><%= prodotto.getNome() %></td>

            <td>€ <%= prodotto.getPrezzo() %></td>

            <td><%= prodotto.getIva() %>%</td>

            <td><%= prodotto.getStock() %></td>

            <td>
                <%
                    /*
                     * Se la categoria non esiste o il nome è nullo,
                     * mostro un trattino per evitare output brutti come "null".
                     */
                    if (prodotto.getNomeCategoria() != null) {
                %>
                <%= prodotto.getNomeCategoria() %>
                <%
                } else {
                %>
                -
                <%
                    }
                %>
            </td>

            <td>
                <%
                    /*
                     * Mostro se il prodotto è attivo o eliminato logicamente.
                     * isEliminato() restituisce true/false.
                     */
                    if (prodotto.isEliminato()) {
                %>
                <span class="badge-eliminato">Eliminato</span>
                <%
                } else {
                %>
                <span class="badge-attivo">Attivo</span>
                <%
                    }
                %>
            </td>

            <td class="azioni">
                <!-- Link alla futura modifica -->
                <a class="modifica"
                   href="${pageContext.request.contextPath}/admin/prodotti/modifica?id=<%= prodotto.getId() %>">
                    Modifica
                </a>

                <!-- Link alla futura eliminazione -->
                <a class="elimina"
                   href="${pageContext.request.contextPath}/admin/prodotti/elimina?id=<%= prodotto.getId() %>">
                    Elimina
                </a>
            </td>
        </tr>
        <%
            }
        %>

        </tbody>
    </table>

    <%
        }
    %>
</div>

</body>
</html>