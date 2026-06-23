<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Catalogo</title>
</head>
<body>
<h1>Catalogo prodotti</h1>

<c:choose>
    <c:when test="${not empty prodotti}">
        <p>Prodotti trovati: ${prodotti.size()}</p>

        <table border="1">
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Descrizione</th>
                <th>Prezzo</th>
                <th>Categoria</th>
                <th>Stock</th>
                <th>Azioni</th>
            </tr>

            <c:forEach var="p" items="${prodotti}">
                <tr>
                    <td>${p.id}</td>
                    <td>${p.nome}</td>
                    <td>${p.descrizione}</td>
                    <td>${p.prezzo}</td>
                    <td>${p.nomeCategoria}</td>
                    <td>${p.stock}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/dettaglio-prodotto?id=${p.id}">
                            Vedi dettaglio
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:when>

    <c:otherwise>
        <p>Nessun prodotto trovato.</p>
    </c:otherwise>
</c:choose>
</body>
</html>