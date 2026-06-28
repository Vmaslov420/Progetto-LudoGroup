<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrazione</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }

        body {
            font-family: Arial, sans-serif;
            background: #f5f5f5;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            padding: 20px;
        }

        .form-container {
            background: white;
            padding: 35px;
            border-radius: 8px;
            box-shadow: 0 2px 12px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 480px;
        }

        h1 { font-size: 24px; margin-bottom: 24px; color: #333; }

        .campo {
            margin-bottom: 18px;
        }

        label {
            display: block;
            font-size: 14px;
            font-weight: bold;
            color: #444;
            margin-bottom: 5px;
        }

        input {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 15px;
            transition: border-color 0.2s;
        }

        input:focus {
            outline: none;
            border-color: #007bff;
        }

        /* Classe aggiunta da JS per campo invalido */
        input.invalido { border-color: #e53935; }
        input.valido   { border-color: #43a047; }

        .msg-errore {
            color: #e53935;
            font-size: 12px;
            margin-top: 4px;
            display: none; /* mostrato da JS */
        }

        .msg-errore.visibile { display: block; }

        .errore-server {
            background: #fdecea;
            color: #c62828;
            border: 1px solid #ef9a9a;
            border-radius: 5px;
            padding: 10px 14px;
            margin-bottom: 16px;
            font-size: 14px;
        }

        button[type="submit"] {
            width: 100%;
            padding: 12px;
            background: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background 0.2s;
            margin-top: 8px;
        }

        button[type="submit"]:hover { background: #0056b3; }

        .link-login {
            text-align: center;
            margin-top: 16px;
            font-size: 14px;
            color: #666;
        }

        .link-login a { color: #007bff; text-decoration: none; }
    </style>
</head>
<body>

<div class="form-container">
    <h1>Crea un account</h1>

    <%-- Messaggio di errore dal server --%>
    <% if (request.getAttribute("errore") != null) { %>
    <div class="errore-server">${errore}</div>
    <% } %>

    <form id="formRegistrazione"
          action="${pageContext.request.contextPath}/registrazione"
          method="post"
          novalidate>

        <div class="campo">
            <label for="nome">Nome *</label>
            <input type="text" id="nome" name="nome"
                   placeholder="Inserisci il tuo nome"
                   autocomplete="given-name">
            <div class="msg-errore" id="err-nome">Il nome non può essere vuoto.</div>
        </div>

        <div class="campo">
            <label for="cognome">Cognome *</label>
            <input type="text" id="cognome" name="cognome"
                   placeholder="Inserisci il tuo cognome"
                   autocomplete="family-name">
            <div class="msg-errore" id="err-cognome">Il cognome non può essere vuoto.</div>
        </div>

        <div class="campo">
            <label for="nickname">Nickname *</label>
            <input type="text" id="nickname" name="nickname"
                   placeholder="Scegli un nickname (min. 3 caratteri)">
            <div class="msg-errore" id="err-nickname">
                Il nickname deve avere almeno 3 caratteri.
            </div>
        </div>

        <div class="campo">
            <label for="email">Email *</label>
            <input type="email" id="email" name="email"
                   placeholder="esempio@email.com"
                   autocomplete="email">
            <div class="msg-errore" id="err-email">Inserisci un indirizzo email valido.</div>
        </div>

        <div class="campo">
            <label for="telefono">Telefono</label>
            <input type="text" id="telefono" name="telefono"
                   placeholder="Opzionale (es. +39 333 1234567)">
            <div class="msg-errore" id="err-telefono">
                Formato non valido (solo cifre, spazi e +).
            </div>
        </div>

        <div class="campo">
            <label for="password">Password *</label>
            <input type="password" id="password" name="password"
                   placeholder="Min. 8 caratteri, almeno una lettera e un numero"
                   autocomplete="new-password">
            <div class="msg-errore" id="err-password">
                Password non valida: minimo 8 caratteri, almeno una lettera e un numero.
            </div>
        </div>

        <div class="campo">
            <label for="confermaPassword">Conferma password *</label>
            <input type="password" id="confermaPassword" name="confermaPassword"
                   placeholder="Ripeti la password"
                   autocomplete="new-password">
            <div class="msg-errore" id="err-conferma">Le password non coincidono.</div>
        </div>

        <button type="submit">Registrati</button>
    </form>

    <div class="link-login">
        Hai già un account?
        <a href="${pageContext.request.contextPath}/login">Accedi</a>
    </div>
</div>

<script>
    // ── Espressioni regolari ──────────────────────────────────────────────────
    const REGEX = {
        nome:     /^[a-zA-ZÀ-ÿ\s']{2,50}$/,
        nickname: /^[a-zA-Z0-9_]{3,50}$/,
        email:    /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
        telefono: /^[\d\s\+\-]{7,20}$/,
        password: /^(?=.*[A-Za-z])(?=.*\d).{8,}$/
    };

    // ── Funzione generica: valida un campo e mostra/nasconde il messaggio ─────
    function validaCampo(inputId, errId, regex, obbligatorio = true) {
        const input = document.getElementById(inputId);
        const err   = document.getElementById(errId);
        const val   = input.value.trim();

        let ok;
        if (!obbligatorio && val === '') {
            ok = true; // campo opzionale vuoto → valido
        } else {
            ok = val !== '' && regex.test(val);
        }

        input.classList.toggle('valido',   ok);
        input.classList.toggle('invalido', !ok);
        err.classList.toggle('visibile',   !ok);
        return ok;
    }

    // ── Controllo AJAX email già presente ─────────────────────────────────────
    let timerEmail = null;
    document.getElementById('email').addEventListener('input', function () {
        clearTimeout(timerEmail);
        const val = this.value.trim();
        if (!REGEX.email.test(val)) return;

        timerEmail = setTimeout(() => {
            fetch('${pageContext.request.contextPath}/verifica-email?email=' +
                encodeURIComponent(val))
                .then(r => r.json())
                .then(data => {
                    const input = document.getElementById('email');
                    const err   = document.getElementById('err-email');
                    if (data.esiste) {
                        input.classList.add('invalido');
                        input.classList.remove('valido');
                        err.textContent = 'Questa email è già registrata.';
                        err.classList.add('visibile');
                    } else {
                        input.classList.add('valido');
                        input.classList.remove('invalido');
                        err.classList.remove('visibile');
                    }
                });
        }, 500); // attende 500ms dopo che l'utente smette di scrivere
    });

    // ── Focus: imposta focus sul primo campo con errore ───────────────────────
    function primoErrore() {
        const primo = document.querySelector('input.invalido');
        if (primo) primo.focus();
    }

    // ── Validazione al submit ─────────────────────────────────────────────────
    document.getElementById('formRegistrazione').addEventListener('submit', function (e) {
        e.preventDefault();

        const nomeOk     = validaCampo('nome',     'err-nome',     REGEX.nome);
        const cognomeOk  = validaCampo('cognome',  'err-cognome',  REGEX.nome);
        const nicknameOk = validaCampo('nickname', 'err-nickname', REGEX.nickname);
        const emailOk    = validaCampo('email',    'err-email',    REGEX.email);
        const telOk      = validaCampo('telefono', 'err-telefono', REGEX.telefono, false);
        const passOk     = validaCampo('password', 'err-password', REGEX.password);

        // Conferma password
        const pass    = document.getElementById('password').value;
        const conf    = document.getElementById('confermaPassword');
        const errConf = document.getElementById('err-conferma');
        const confOk  = conf.value === pass && pass !== '';
        conf.classList.toggle('valido',   confOk);
        conf.classList.toggle('invalido', !confOk);
        errConf.classList.toggle('visibile', !confOk);

        if (nomeOk && cognomeOk && nicknameOk && emailOk && telOk && passOk && confOk) {
            this.submit(); // tutti i campi validi → invia al server
        } else {
            primoErrore(); // focus sul primo campo errato
        }
    });
</script>

</body>
</html>