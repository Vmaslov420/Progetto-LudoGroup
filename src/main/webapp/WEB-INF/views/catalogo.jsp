<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Catalogo</title>
</head>
<body>

<jsp:include page="/WEB-INF/views/fragments/header.jsp" />

<h1>Catalogo prodotti</h1>

<c:choose>
    <c:when test="${not empty prodotti}">
        <p>Prodotti trovati: ${fn:length(prodotti)}</p>

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

                    <td>
                        <c:choose>
                            <c:when test="${not empty p.nomeCategoria}">
                                ${p.nomeCategoria}
                            </c:when>
                            <c:otherwise>
                                -
                            </c:otherwise>
                        </c:choose>
                    </td>

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