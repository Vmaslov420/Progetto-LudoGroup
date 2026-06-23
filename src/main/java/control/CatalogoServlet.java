package control;

import model.bean.Prodotto;
import model.dao.ProdottoDAO;

// QUESTI sono gli import corretti per Tomcat 11
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/catalogo")
public class CatalogoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ProdottoDAO dao = new ProdottoDAO();
            List<Prodotto> prodotti = dao.getTutti();
            request.setAttribute("prodotti", prodotti);
            request.getRequestDispatcher("/WEB-INF/views/catalogo.jsp")
                    .forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Errore DB: " + e.getMessage(), e);
        }
    }
}