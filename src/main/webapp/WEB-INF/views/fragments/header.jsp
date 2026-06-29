<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.bean.Utente" %>

<%
    Utente utenteLoggato = (Utente) session.getAttribute("utente");

    /*
     * true solo se:
     * - esiste un utente loggato
     * - il ruolo non è null
     * - il ruolo vale "admin"
     */
    boolean admin = utenteLoggato != null
            && utenteLoggato.getRuolo() != null
            && "admin".equalsIgnoreCase(utenteLoggato.getRuolo());
%>

<header style="padding: 15px 30px; background: #ffffff; border-bottom: 1px solid #ddd;">

    <div style="display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 12px;">

        <a href="${pageContext.request.contextPath}/"
           style="text-decoration: none; color: #333; font-size: 22px; font-weight: bold;">
            LudoGroup
        </a>

        <nav style="display: flex; gap: 15px; align-items: center; flex-wrap: wrap;">

            <a href="${pageContext.request.contextPath}/"
               style="text-decoration: none; color: #333;">
                Home
            </a>

            <a href="${pageContext.request.contextPath}/catalogo"
               style="text-decoration: none; color: #333;">
                Catalogo
            </a>

            <%
                if (utenteLoggato != null) {
            %>

            <a href="${pageContext.request.contextPath}/profilo"
               style="text-decoration: none; color: #333;">
                Profilo
            </a>

            <%
                /*
                 * Questo link compare SOLO agli admin.
                 */
                if (admin) {
            %>
            <a href="${pageContext.request.contextPath}/admin/prodotti"
               style="text-decoration: none; color: #7b1fa2; font-weight: bold;">
                Area Admin
            </a>
            <%
                }
            %>

            <span style="color: #333;">
                Ciao, <%= utenteLoggato.getNickname() %>
            </span>



            <a href="${pageContext.request.contextPath}/logout"
               style="text-decoration: none; color: #c62828;">
                Logout
            </a>

            <%
            } else {
            %>

            <a href="${pageContext.request.contextPath}/login"
               style="text-decoration: none; color: #007bff;">
                Login
            </a>

            <a href="${pageContext.request.contextPath}/registrazione"
               style="text-decoration: none; color: #007bff;">
                Registrati
            </a>

            <%
                }
            %>

        </nav>
    </div>
</header>