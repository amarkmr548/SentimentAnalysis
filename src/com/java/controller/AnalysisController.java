package com.java.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.java.sentimentanalyser.ReviewProcessor;

public class AnalysisController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    private static final String DATA_DIRECTORY = "data";
    private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	ReviewProcessor reviewprocessor = new ReviewProcessor();
        File outputfile = new File(System.getProperty("java.io.tmpdir") + "output.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputfile));
        BufferedReader br = null;
        
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (!isMultipart) {
            return;
        }

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Sets the size threshold beyond which files are written directly to
        // disk.
        factory.setSizeThreshold(MAX_MEMORY_SIZE);

        
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        // constructs the folder where uploaded file will be stored
        String uploadFolder = getServletContext().getRealPath("")
                + File.separator + DATA_DIRECTORY;

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Set overall request size constraint
        upload.setSizeMax(MAX_REQUEST_SIZE);

        try {
            // Parse the request
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (!item.isFormField()) {
                    String fileName = new File(item.getName()).getName();
                    String filePath = uploadFolder + File.separator + fileName;
                    File uploadedFile = new File(filePath);
                    
                    br = new BufferedReader(new InputStreamReader(item.getInputStream()));
                    String str;
                    while((str = br.readLine())!= null){
                    	String review = reviewprocessor.processReview(str);
                    	System.out.println(str);
                    	bw.write(review+"\r\n");
                    }
                    System.out.println(filePath);
                    // saves the file to upload directory
                    //item.write(uploadedFile);
                }
            }
            
            if(bw != null){
            	bw.close();
            }
            if(br != null){
            	br.close();
            }
            PrintWriter out = response.getWriter();
            response.setContentType("application/force-download");
            response.setContentLength((int)outputfile.length());
                    //response.setContentLength(-1);
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setHeader("Content-Disposition","attachment; filename=\"output.txt\"");
            response.setBufferSize(MAX_MEMORY_SIZE);
            FileReader in = new FileReader(outputfile);
            BufferedReader bin = new BufferedReader(in);
            //DataInputStream din = new DataInputStream(bin);
            String outputresult;

            while((outputresult = bin.readLine())!= null){
                out.print(outputresult);
                out.print("\r\n");
            }
           
           if(in != null){
        	   in.close();
           }

           if(bin != null){
        	   bin.close();
           }
            
            // displays done.jsp page after upload finished
           // getServletContext().getRequestDispatcher("/jsp/done.jsp").forward(
             //       request, response);

        } catch (FileUploadException ex) {
            throw new ServletException(ex);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
        finally{
        	if(bw != null){
        		bw.close();
        	}
        	if(br != null){
        		br.close();
        	}
        	
        }
    	

    }
}
