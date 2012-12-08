package pl.kwi.servlets;

import java.io.IOException;

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

@WebServlet("/create.do")
public class CreateServlet extends HttpServlet{
	
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(CreateServlet.class);
	

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		String submit = request.getParameter("submit");
		
		if("Init".equals(submit)){
			submitInit(request, response);
			return;
		}else if("OK".equals(submit)){
			submitOK(request, response);
			return;
		}else if("Back".equals(submit)){
			submitBack(request, response);
			return;
		}
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/CreateJSP.jsp");
		requestDispatcher.forward(request, response);		
		
	}
	
	private void submitInit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/CreateJSP.jsp");
		requestDispatcher.forward(request, response);
		
	}
	
	private void submitOK(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String userName = request.getParameter("userName");
		
		UserEntity entity = new UserEntity();
		entity.setName(userName);
		UserService service = new UserService();
		service.create(entity);

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/table.do?submit=Init");
		requestDispatcher.forward(request, response);
		
	}
	
	private void submitBack(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/table.do?submit=Init");
		requestDispatcher.forward(request, response);
		
	}

}
