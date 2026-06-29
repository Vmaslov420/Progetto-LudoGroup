<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.bean.Utente" %>

<%
    /*
     * Recuperiamo l'utente dalla sessione.
     */
    Utente utenteLoggato = (Utente) session.getAttribute("utente");

    /*
     * L'utente è admin solo se:
     * - è loggato
     * - il ruolo non è null
     * - il ruolo vale "admin"
     */
    boolean admin = utenteLoggato != null
            && utenteLoggato.getRuolo() != null
            && "admin".equalsIgnoreCase(utenteLoggato.getRuolo());
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - LudoGroup</title>

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f5f7fb;
            color: #222;
        }

        .hero {
            max-width: 1100px;
            margin: 40px auto;
            padding: 40px 20px;
        }

        .hero-box {
            background: linear-gradient(135deg, #ffffff, #eef4ff);
            border: 1px solid #d9e2f0;
            border-radius: 14px;
            padding: 40px;
            box-shadow: 0 4px 18px rgba(0,0,0,0.06);
        }

        .badge {
            display: inline-block;
            background: #e8f0ff;
            color: #1e4fa3;
            border-radius: 999px;
            padding: 8px 14px;
            font-size: 13px;
            font-weight: bold;
            margin-bottom: 18px;
        }

        h1 {
            margin-top: 0;
            font-size: 38px;
            line-height: 1.2;
            margin-bottom: 16px;
        }

        .sottotitolo {
            font-size: 18px;
            color: #555;
            max-width: 700px;
            line-height: 1.6;
            margin-bottom: 28px;
        }

        .azioni {
            display: flex;
            flex-wrap: wrap;
            gap: 14px;
            margin-top: 25px;
        }

        .bottone {
            display: inline-block;
            padding: 12px 20px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: bold;
            transition: 0.2s ease;
        }

        .bottone-primario {
            background: #2563eb;
            color: white;
        }

        .bottone-primario:hover {
            background: #1d4ed8;
        }

        .bottone-secondario {
            background: white;
            color: #222;
            border: 1px solid #cfd7e6;
        }

        .bottone-secondario:hover {
            background: #f3f4f6;
        }

        .sezione {
            max-width: 1100px;
            margin: 0 auto 40px auto;
            padding: 0 20px;
        }

        .griglia {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
            gap: 20px;
        }

        .card {
            background: white;
            border: 1px solid #e0e0e0;
            border-radius: 12px;
            padding: 22px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.04);
        }

        .card h3 {
            margin-top: 0;
            margin-bottom: 10px;
        }

        .card p {
            margin: 0 0 15px 0;
            color: #555;
            line-height: 1.5;
        }

        .card a {
            text-decoration: none;
            font-weight: bold;
            color: #2563eb;
        }

        @media (max-width: 768px) {
            h1 {
                font-size: 30px;
            }

            .hero-box {
                padding: 28px 22px;
            }

            .sottotitolo {
                font-size: 16px;
            }
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/fragments/header.jsp" />

<section class="hero">
    <div class="hero-box">

        <div class="badge">Benvenuto su LudoGroup</div>

        <h1>Il tuo shop online giochi da tavolo e giochi di compagnia</h1>

        <p class="sottotitolo">
            Esplora il catalogo, consulta i dettagli dei prodotti
            e naviga facilmente tra le funzionalità del sito senza dover digitare ogni URL manualmente.
        </p>

        <div class="azioni">
            <a class="bottone bottone-primario"
               href="${pageContext.request.contextPath}/catalogo">
                Vai al catalogo
            </a>

            <%
                if (utenteLoggato == null) {
            %>
            <a class="bottone bottone-secondario"
               href="${pageContext.request.contextPath}/login">
                Accedi
            </a>

            <a class="bottone bottone-secondario"
               href="${pageContext.request.contextPath}/registrazione">
                Registrati
            </a>
            <%
            } else {
            %>
            <a class="bottone bottone-secondario"
               href="${pageContext.request.contextPath}/profilo">
                Vai al profilo
            </a>
            <%
                }
            %>

            <%
                if (admin) {
            %>
            <a class="bottone bottone-secondario"
               href="${pageContext.request.contextPath}/admin/prodotti">
                Gestisci prodotti
            </a>
            <%
                }
            %>
        </div>
    </div>
</section>

<section class="sezione">
    <div class="griglia">

        <div class="card">
            <h3>Catalogo prodotti</h3>
            <p>
                Visualizza i prodotti disponibili, consulta prezzi,
                categorie e dettagli completi.
            </p>
            <a href="${pageContext.request.contextPath}/catalogo">Apri catalogo</a>
        </div>

        <div class="card">
            <h3>Area personale</h3>
            <p>
                Accedi al tuo profilo per gestire le informazioni
                dell’account e le funzionalità riservate.
            </p>
            <a href="${pageContext.request.contextPath}/profilo">Vai al profilo</a>
        </div>

        <%
            if (admin) {
        %>
        <div class="card">
            <h3>Area amministratore</h3>
            <p>
                Gli amministratori possono inserire, modificare
                ed eliminare prodotti dal catalogo.
            </p>
            <a href="${pageContext.request.contextPath}/admin/prodotti">Apri area admin</a>
        </div>
        <%
            }
        %>

    </div>
</section>

</body>
</html>