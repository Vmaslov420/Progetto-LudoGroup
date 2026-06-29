<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.bean.Prodotto" %>
<%@ page import="model.bean.Categoria" %>
<%@ page import="java.util.List" %>



<%
    /*
     * Usiamo la stessa JSP poichè tra inserimento e modifica
     * i campi sono quasi identici e differiscono per titolo, action e
     * valori iniziali
     * Recuperiamo il prodotto dalla request.
     * In modalità "nuovo" probabilmente sarà null.
     * In modalità "modifica" conterrà i dati esistenti.
     */
    Prodotto prodotto = (Prodotto) request.getAttribute("prodotto");
    /*
     * Servlet carica il prodotto dal DB nella request e JSP legge i valori per riempire i campi
     */




    /*
     * Questo attributo ci dice se siamo in inserimento o modifica.
     */
    String modalita = (String) request.getAttribute("modalita");

    List<Categoria> listaCategorie = (List<Categoria>) request.getAttribute("listaCategorie");



    /*
     * Se modalita non è valorizzata, consideriamo di default "nuovo".
     */
    if (modalita == null) {
        modalita = "nuovo";
    }

    boolean modifica = "modifica".equals(modalita);

    /*
     * Preparo valori sicuri da stampare nei campi.
     * Se prodotto è null, evito NullPointerException e mostro stringhe vuote.
     */
    String nome = (prodotto != null && prodotto.getNome() != null) ? prodotto.getNome() : "";
    String descrizione = (prodotto != null && prodotto.getDescrizione() != null) ? prodotto.getDescrizione() : "";
    String immagine = (prodotto != null && prodotto.getImmagine() != null) ? prodotto.getImmagine() : "";

    String prezzo = (prodotto != null && prodotto.getPrezzo() != null) ? prodotto.getPrezzo().toString() : "";
    String iva = (prodotto != null && prodotto.getIva() != null) ? prodotto.getIva().toString() : "22.00";
    String stock = (prodotto != null) ? String.valueOf(prodotto.getStock()) : "0";

    String errore = (String) request.getAttribute("errore");


    Integer categoriaSelezionata = (prodotto != null) ? prodotto.getCategoriaId() : null;

    /*
     * Cambiamo action e titolo in base alla modalità.
     */
    String actionForm = modifica
            ? request.getContextPath() + "/admin/prodotti/modifica"
            : request.getContextPath() + "/admin/prodotti/nuovo";

    String titoloPagina = modifica
            ? "Modifica prodotto"
            : "Inserisci nuovo prodotto";

    String testoBottone = modifica
            ? "Salva modifiche"
            : "Salva prodotto";
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= titoloPagina %></title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f6f8;
            margin: 0;
            padding: 0;
        }

        .contenitore {
            max-width: 800px;
            margin: 30px auto;
            background: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 12px rgba(0,0,0,0.08);
        }

        h1 {
            margin-bottom: 20px;
            color: #222;
        }

        .errore {
            background: #fdecea;
            color: #b71c1c;
            padding: 12px;
            border-radius: 6px;
            margin-bottom: 20px;
        }

        .gruppo-form {
            margin-bottom: 18px;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-weight: bold;
            color: #333;
        }

        input, textarea, select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
            box-sizing: border-box;
        }

        textarea {
            min-height: 120px;
            resize: vertical;
        }

        .azioni {
            display: flex;
            gap: 10px;
            margin-top: 20px;
        }

        .bottone {
            display: inline-block;
            padding: 10px 16px;
            border-radius: 6px;
            text-decoration: none;
            font-weight: bold;
            border: none;
            cursor: pointer;
        }

        .bottone-salva {
            background: #007bff;
            color: white;
        }

        .bottone-annulla {
            background: #6c757d;
            color: white;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/fragments/header.jsp" />

<div class="contenitore">
    <h1><%= titoloPagina %></h1>

    <%
        if (errore != null) {
    %>
    <div class="errore"><%= errore %></div>
    <%
        }
    %>

    <form action="<%= actionForm %>" method="post">

        <%
            /*
             * In modifica ci serve l'id del prodotto.
             * Lo inviamo come campo hidden, così il browser non lo mostra
             * ma la servlet lo riceve comunque in POST.
             */
            if (modifica && prodotto != null) {
        %>
        <input type="hidden" name="id" value="<%= prodotto.getId() %>">
        <%
            }
        %>

        <div class="gruppo-form">
            <label for="nome">Nome prodotto *</label>
            <input type="text" id="nome" name="nome" value="<%= nome %>" required>
        </div>

        <div class="gruppo-form">
            <label for="descrizione">Descrizione</label>
            <textarea id="descrizione" name="descrizione"><%= descrizione %></textarea>
        </div>

        <div class="gruppo-form">
            <label for="prezzo">Prezzo *</label>
            <input type="number" id="prezzo" name="prezzo" step="0.01" min="0" value="<%= prezzo %>" required>
        </div>

        <div class="gruppo-form">
            <label for="iva">IVA *</label>
            <input type="number" id="iva" name="iva" step="0.01" min="0" value="<%= iva %>" required>
        </div>

        <div class="gruppo-form">
            <label for="stock">Stock *</label>
            <input type="number" id="stock" name="stock" min="0" value="<%= stock %>" required>
        </div>

        <div class="gruppo-form">
            <label for="immagine">Immagine</label>
            <input type="text" id="immagine" name="immagine" value="<%= immagine %>" placeholder="es. mouse.jpg">
        </div>

        <div class="gruppo-form">
            <label for="categoriaId">Categoria</label>
            <select id="categoriaId" name="categoriaId">
                <option value="">-- Nessuna categoria --</option>

                <%
                    if (listaCategorie != null) {
                        for (Categoria cat : listaCategorie)  {
                            boolean selezionata = categoriaSelezionata != null
                                && categoriaSelezionata == cat.getId();
                %>
                    <option value="<%= cat.getId() %>" <%= selezionata ? "selected" : "" %>>
                        <%= cat.getNome()%>
                    </option>
                <%
                        }
                     }
                %>
            </select>
        </div>

        <div class="azioni">
            <button type="submit" class="bottone bottone-salva"><%= testoBottone %></button>

            <a class="bottone bottone-annulla"
               href="${pageContext.request.contextPath}/admin/prodotti">
                Annulla
            </a>
        </div>
    </form>
</div>

</body>
</html>