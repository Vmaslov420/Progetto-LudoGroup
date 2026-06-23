<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
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
    </style>
</head>
<body>

<div class="contenitore">
    <h1>${prodotto.nome}</h1>

    <p><strong>Categoria:</strong> ${prodotto.nomeCategoria}</p>
    <p><strong>Descrizione:</strong> ${prodotto.descrizione}</p>
    <p class="prezzo">€ ${prodotto.prezzo}</p>
    <p><strong>IVA:</strong> ${prodotto.iva}%</p>
    <p><strong>Disponibilità:</strong> ${prodotto.stock}</p>

    <div class="azioni">
        <a href="${pageContext.request.contextPath}/catalogo">Torna al catalogo</a>
    </div>
</div>

</body>
</html>