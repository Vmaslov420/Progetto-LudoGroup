<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dettaglio prodotto</title>
    
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css">
</head>

<!-- Assegnazione della classe per lo sfondo della pagina -->
<body class="prodotto-body">

<!-- Scheda centrale che fa da contenitore a tutte le informazioni visive -->
<div class="contenitore-prodotto">

   
    <h1 class="prodotto-titolo">${prodotto.nome}</h1>

   
    <p class="prodotto-info"><strong>Categoria:</strong> ${prodotto.nomeCategoria}</p>
    <p class="prodotto-info"><strong>Descrizione:</strong> ${prodotto.descrizione}</p>
    <p class="prodotto-prezzo">€ ${prodotto.prezzo}</p>
    <p class="prodotto-info"><strong>IVA:</strong> ${prodotto.iva}%</p>
    <p class="prodotto-info"><strong>Disponibilità:</strong> ${prodotto.stock}</p>

    <!-- Sezione contenente i pulsanti di navigazione dell'utente -->
    <div class="prodotto-azioni">
       
        <a href="${pageContext.request.contextPath}/catalogo">Torna al catalogo</a>
    </div>
</div>

</body>
</html>