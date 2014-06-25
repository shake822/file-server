package com.comtop.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class FileServlet
 */
@WebServlet("/FileServlet")
public class FileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	 public void doPost(HttpServletRequest request, HttpServletResponse response)  
	            throws ServletException, IOException {  
	          
	        boolean isMultipart = ServletFileUpload.isMultipartContent(request);  
	        if(isMultipart){  
	            String realPath = "D:\\CSSO\\fileUpload\\";  
	              
	            System.out.println(realPath);  
	              
	            File dir = new File(realPath);  
	              
	            if(!dir.exists()){  
	                dir.mkdir();  
	            }  
	              
	            DiskFileItemFactory factory = new DiskFileItemFactory();  
	            ServletFileUpload upload = new ServletFileUpload(factory);  
	              
	            upload.setHeaderEncoding("utf-8");  
	              
	            try {  
	                List<FileItem> items = upload.parseRequest(request);  
	                   
	                for(FileItem item : items){  
	                    if(item.isFormField()){ //username="username"  
	                        String name = item.getFieldName();  
	                        String value = item.getString("utf-8");  
	                          
	                        System.out.println(name + " = " + value);  
	                    } else { //нд╪Ч  
	                        String name = item.getName();  
	                        item.write(new File(dir, System.currentTimeMillis() + name.substring(name.lastIndexOf("."))));  
	                          
	                    }  
	                      
	                }  
	                  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	 
	}    


