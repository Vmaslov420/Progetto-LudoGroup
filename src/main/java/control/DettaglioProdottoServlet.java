package control;

import model.bean.Prodotto;
import model.dao.ProdottoDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/dettaglio-prodotto")
public class DettaglioProdottoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID prodotto mancante");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);

            ProdottoDAO prodottoDAO = new ProdottoDAO();
            Prodotto prodotto = prodottoDAO.getById(id);

            if (prodotto == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Prodotto non trovato");
                return;
            }

            request.setAttribute("prodotto", prodotto);
            request.getRequestDispatcher("/WEB-INF/views/dettaglio-prodotto.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID prodotto non valido");
        } catch (SQLException e) {
            throw new ServletException("Errore nel recupero del prodotto", e);
        }
    }
}