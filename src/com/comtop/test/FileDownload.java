package com.comtop.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileDownload
 */
@WebServlet("/FileDownload")
public class FileDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileDownload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.downRanFile(request, response);
	}

	 public HttpServletResponse download(HttpServletRequest request, HttpServletResponse response) {
	        try {
	            // path是指欲下载的文件的路径。
	            File file = new File("D:\\CSSO\\fileUpload\\1403665709499.rar");
	            // 取得文件名。
	            String filename = file.getName();
	            // 取得文件的后缀名。
	            //String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

	            // 以流的形式下载文件。
	            InputStream fis = new BufferedInputStream(new FileInputStream(file));
	            byte[] buffer = new byte[fis.available()];
	            fis.read(buffer);
	            fis.close();
	            // 清空response
	            response.reset();
	            // 设置response的Header
	            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("utf-8"),"gbk"));
	            response.setHeader("Accept-Ranges", "bytes");  
	            response.addHeader("Content-Range", "bytes=" + file.length());
	            response.addHeader("Content-Length", "" + file.length());
	            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
	            response.setContentType("application/octet-stream");
	            toClient.write(buffer);
	            toClient.flush();
	            toClient.close();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	        return response;
	    }
	 
	 private void downRanFile(HttpServletRequest request, HttpServletResponse response){
		 try {
		 /*  
		   文件名可存为: Download.jsp  
		   HTTP 协议的请求与响应的会话过程可通过使用 FlashGet 下载 Http:// 连接的过程监视:  
		   蓝色部分为: 客户端请求  
		   紫色部分为: 服务器端响应  
		   如图:  
		   http://blog.csdn.net/images/blog_csdn_net/playyuer/30110/o_FlashGet.gif  
		   或参阅,后面的 FlashGet 会话列表:  
		    
		*/   
		  //你可以使用你服务器上的文件及其路径   
		  String s = "D:\\CSSO\\fileUpload\\1403665709499.rar";   
		  //String s = "e://tree.mdb";   
		  
		  //经测试 RandomAccessFile 也可以实现,有兴趣可将注释去掉,并注释掉 FileInputStream 版本的语句   
		  //java.io.RandomAccessFile raf = new java.io.RandomAccessFile(s,"r");   
		  
		  java.io.File f = new java.io.File(s);   
		  java.io.FileInputStream fis = new java.io.FileInputStream(f);   
		  
		  response.reset();   
		  
		  response.setHeader("Server", "playyuer@Microshaoft.com");   
		  
		  //告诉客户端允许断点续传多线程连接下载   
		  //响应的格式是:   
		  //Accept-Ranges: bytes   
		  response.setHeader("Accept-Ranges", "bytes");   
		  
		  long p = 0;   
		  long l = 0;   
		  //l = raf.length();   
		  l = f.length();   
		  
		  //如果是第一次下,还没有断点续传,状态是默认的 200,无需显式设置   
		  //响应的格式是:   
		  //HTTP/1.1 200 OK   
		  
		  if (request.getHeader("Range") != null) //客户端请求的下载的文件块的开始字节   
		  {   
		   //如果是下载文件的范围而不是全部,向客户端声明支持并开始文件块下载   
		   //要设置状态   
		   //响应的格式是:   
		   //HTTP/1.1 206 Partial Content   
		   response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);//206   
		  
		   //从请求中得到开始的字节   
		   //请求的格式是:   
		   //Range: bytes=[文件块的开始字节]-   
		   p = Long.parseLong(request.getHeader("Range").replaceAll("bytes=","").replaceAll("-",""));   
		  }   
		  
		  //下载的文件(或块)长度   
		  //响应的格式是:   
		  //Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]   
		  response.setHeader("Content-Length", new Long(l - p).toString());   
		  
		  if (p != 0)   
		  {   
		   //不是从最开始下载,   
		   //响应的格式是:   
		   //Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]   
		   response.setHeader("Content-Range","bytes " + new Long(p).toString() + "-" + new Long(l -1).toString() + "/" + new Long(l).toString());   
		  }   
		  
		  //response.setHeader("Connection", "Close"); //如果有此句话不能用 IE 直接下载   
		  
		  //使客户端直接下载   
		  //响应的格式是:   
		  //Content-Type: application/octet-stream   
		  response.setContentType("application/octet-stream");   
		  
		  //为客户端下载指定默认的下载文件名称   
		  //响应的格式是:   
		  //Content-Disposition: attachment;filename="[文件名]"   
		  //response.setHeader("Content-Disposition", "attachment;filename=/"" + s.substring(s.lastIndexOf("//") + 1) + "/""); //经测试 RandomAccessFile 也可以实现,有兴趣可将注释去掉,并注释掉 FileInputStream 版本的语句   
		  response.setHeader("Content-Disposition", "attachment;filename=" + f.getName() );   
		  
		  //raf.seek(p);   
		  
			fis.skip(p);
		
		  
		  byte[] b = new byte[1024];   
		  int i;   
		  
		  
		  //while ( (i = raf.read(b)) != -1 ) //经测试 RandomAccessFile 也可以实现,有兴趣可将注释去掉,并注释掉 FileInputStream 版本的语句   
		  while ( (i = fis.read(b)) != -1 )   
		  {   
		   response.getOutputStream().write(b,0,i);   
		  }   
		  //raf.close();//经测试 RandomAccessFile 也可以实现,有兴趣可将注释去掉,并注释掉 FileInputStream 版本的语句   
		  fis.close();   
		  } catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}   
	 }
}
