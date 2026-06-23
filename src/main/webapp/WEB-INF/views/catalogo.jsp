<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Catalogo - Test</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .ok { color: green; font-weight: bold; }
        .errore { color: red; font-weight: bold; }
    </style>
</head>
<body>

<h1>🎲 Catalogo Giochi — Pagina di Test</h1>

<!-- Mostra quanti prodotti ha trovato nel DB -->
<c:choose>
    <c:when test="${not empty prodotti}">
        <p class="ok">✅ Connessione al DB riuscita!
            Trovati <strong>${prodotti.size()}</strong> prodotti.</p>

        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Categoria</th>
                <th>Prezzo</th>
                <th>IVA</th>
                <th>Stock</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="p" items="${prodotti}">
                <tr>
                    <td>${p.id}</td>
                    <td>${p.nome}</td>
                    <td>${p.nomeCategoria}</td>
                    <td>€ ${p.prezzo}</td>
                    <td>${p.iva}%</td>
                    <td>${p.stock}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p class="errore">❌ Nessun prodotto trovato.
            Controlla la connessione al DB o i dati inseriti.</p>
    </c:otherwise>
</c:choose>

</body>
</html>