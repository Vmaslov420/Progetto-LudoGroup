<!-- Configurazione JSP e importazione delle librerie JSTL core per i cicli e i controlli -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Catalogo</title>
    <!-- FRONT-END: Collegamento al file CSS unico condiviso dell'applicazione -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css">
</head>
<body>

<h1 class="catalogo-titolo">Catalogo prodotti</h1>

<c:choose>
    <c:when test="${not empty prodotti}">
        
        <!-- Calcola e mostra dinamicamente il numero totale di prodotti -->
        <p class="catalogo-info">Prodotti trovati: ${prodotti.size()}</p>

        
        <table class="catalogo-tabella">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Descrizione</th>
                    <th>Prezzo</th>
                    <th>Categoria</th>
                    <th>Stock</th>
                    <th>Azioni</th>
                </tr>
            </thead>
            <tbody>
                
                <c:forEach var="p" items="${prodotti}">
                    <tr>
                        <td>${p.id}</td>
                        <td>${p.nome}</td>
                        <td>${p.descrizione}</td>
                        <td>€ ${p.prezzo}</td>
                        <td>${p.nomeCategoria}</td>
                        <td>${p.stock}</td>
                        <td>
                            
                            <!-- Trasformato in un pulsante grazie alla classe .btn-dettaglio -->
                            <a href="${pageContext.request.contextPath}/dettaglio-prodotto?id=${p.id}" class="btn-dettaglio">
                                Vedi dettaglio
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>

    <!-- Eseguito se la lista dei prodotti nel database è vuota -->
    <c:otherwise>
        <p class="catalogo-info">Nessun prodotto trovato.</p>
    </c:otherwise>
</c:choose>

</body>
</html>