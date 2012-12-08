package pl.kwi.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.kwi.entities.UserEntity;
import pl.kwi.services.UserService;

@WebServlet("/table.do")
public class TableServlet extends HttpServlet{
	
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(TableServlet.class);
	

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		String submit = request.getParameter("submit");
		
		if("Init".equals(submit)){
			submitInit(request, response);
			return;
		}else if("View".equals(submit)){
			submitView(request, response);
			return;
		}else if("Create".equals(submit)){
			submitCreate(request, response);
			return;
		}else if("Edit".equals(submit)){
			submitEdit(request, response);
			return;
		}else if("Delete".equals(submit)){
			submitDelete(request, response);
			return;
		}
				
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/TableJSP.jsp");
		requestDispatcher.forward(request, response);		
		
	}
	
	private void submitInit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		UserService service = new UserService();
		List<UserEntity> users = service.getUsers();		
		request.setAttribute("users", users);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/TableJSP.jsp");
		requestDispatcher.forward(request, response);
		
	}
	
	private void submitView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/view.do?submit=Init");
		requestDispatcher.forward(request, response);
		
	}
	
	private void submitCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/create.do?submit=Init");
		requestDispatcher.forward(request, response);
		
	}
	
	private void submitEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String id = request.getParameter("id");
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/edit.do?submit=Init&id=" + id);
		requestDispatcher.forward(request, response);
		
	}
	
	private void submitDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String id = request.getParameter("id");
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/delete.do?submit=Init&id=" + id);
		requestDispatcher.forward(request, response);
		
	}
		
}
