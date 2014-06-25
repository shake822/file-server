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
	            // path��ָ�����ص��ļ���·����
	            File file = new File("D:\\CSSO\\fileUpload\\1403665709499.rar");
	            // ȡ���ļ�����
	            String filename = file.getName();
	            // ȡ���ļ��ĺ�׺����
	            //String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

	            // ��������ʽ�����ļ���
	            InputStream fis = new BufferedInputStream(new FileInputStream(file));
	            byte[] buffer = new byte[fis.available()];
	            fis.read(buffer);
	            fis.close();
	            // ���response
	            response.reset();
	            // ����response��Header
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
		   �ļ����ɴ�Ϊ: Download.jsp  
		   HTTP Э�����������Ӧ�ĻỰ���̿�ͨ��ʹ�� FlashGet ���� Http:// ���ӵĹ��̼���:  
		   ��ɫ����Ϊ: �ͻ�������  
		   ��ɫ����Ϊ: ����������Ӧ  
		   ��ͼ:  
		   http://blog.csdn.net/images/blog_csdn_net/playyuer/30110/o_FlashGet.gif  
		   �����,����� FlashGet �Ự�б�:  
		    
		*/   
		  //�����ʹ����������ϵ��ļ�����·��   
		  String s = "D:\\CSSO\\fileUpload\\1403665709499.rar";   
		  //String s = "e://tree.mdb";   
		  
		  //������ RandomAccessFile Ҳ����ʵ��,����Ȥ�ɽ�ע��ȥ��,��ע�͵� FileInputStream �汾�����   
		  //java.io.RandomAccessFile raf = new java.io.RandomAccessFile(s,"r");   
		  
		  java.io.File f = new java.io.File(s);   
		  java.io.FileInputStream fis = new java.io.FileInputStream(f);   
		  
		  response.reset();   
		  
		  response.setHeader("Server", "playyuer@Microshaoft.com");   
		  
		  //���߿ͻ�������ϵ��������߳���������   
		  //��Ӧ�ĸ�ʽ��:   
		  //Accept-Ranges: bytes   
		  response.setHeader("Accept-Ranges", "bytes");   
		  
		  long p = 0;   
		  long l = 0;   
		  //l = raf.length();   
		  l = f.length();   
		  
		  //����ǵ�һ����,��û�жϵ�����,״̬��Ĭ�ϵ� 200,������ʽ����   
		  //��Ӧ�ĸ�ʽ��:   
		  //HTTP/1.1 200 OK   
		  
		  if (request.getHeader("Range") != null) //�ͻ�����������ص��ļ���Ŀ�ʼ�ֽ�   
		  {   
		   //����������ļ��ķ�Χ������ȫ��,��ͻ�������֧�ֲ���ʼ�ļ�������   
		   //Ҫ����״̬   
		   //��Ӧ�ĸ�ʽ��:   
		   //HTTP/1.1 206 Partial Content   
		   response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);//206   
		  
		   //�������еõ���ʼ���ֽ�   
		   //����ĸ�ʽ��:   
		   //Range: bytes=[�ļ���Ŀ�ʼ�ֽ�]-   
		   p = Long.parseLong(request.getHeader("Range").replaceAll("bytes=","").replaceAll("-",""));   
		  }   
		  
		  //���ص��ļ�(���)����   
		  //��Ӧ�ĸ�ʽ��:   
		  //Content-Length: [�ļ����ܴ�С] - [�ͻ�����������ص��ļ���Ŀ�ʼ�ֽ�]   
		  response.setHeader("Content-Length", new Long(l - p).toString());   
		  
		  if (p != 0)   
		  {   
		   //���Ǵ��ʼ����,   
		   //��Ӧ�ĸ�ʽ��:   
		   //Content-Range: bytes [�ļ���Ŀ�ʼ�ֽ�]-[�ļ����ܴ�С - 1]/[�ļ����ܴ�С]   
		   response.setHeader("Content-Range","bytes " + new Long(p).toString() + "-" + new Long(l -1).toString() + "/" + new Long(l).toString());   
		  }   
		  
		  //response.setHeader("Connection", "Close"); //����д˾仰������ IE ֱ������   
		  
		  //ʹ�ͻ���ֱ������   
		  //��Ӧ�ĸ�ʽ��:   
		  //Content-Type: application/octet-stream   
		  response.setContentType("application/octet-stream");   
		  
		  //Ϊ�ͻ�������ָ��Ĭ�ϵ������ļ�����   
		  //��Ӧ�ĸ�ʽ��:   
		  //Content-Disposition: attachment;filename="[�ļ���]"   
		  //response.setHeader("Content-Disposition", "attachment;filename=/"" + s.substring(s.lastIndexOf("//") + 1) + "/""); //������ RandomAccessFile Ҳ����ʵ��,����Ȥ�ɽ�ע��ȥ��,��ע�͵� FileInputStream �汾�����   
		  response.setHeader("Content-Disposition", "attachment;filename=" + f.getName() );   
		  
		  //raf.seek(p);   
		  
			fis.skip(p);
		
		  
		  byte[] b = new byte[1024];   
		  int i;   
		  
		  
		  //while ( (i = raf.read(b)) != -1 ) //������ RandomAccessFile Ҳ����ʵ��,����Ȥ�ɽ�ע��ȥ��,��ע�͵� FileInputStream �汾�����   
		  while ( (i = fis.read(b)) != -1 )   
		  {   
		   response.getOutputStream().write(b,0,i);   
		  }   
		  //raf.close();//������ RandomAccessFile Ҳ����ʵ��,����Ȥ�ɽ�ע��ȥ��,��ע�͵� FileInputStream �汾�����   
		  fis.close();   
		  } catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}   
	 }
}
