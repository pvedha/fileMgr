package com.howtodoinjava.jersey;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.jvnet.hk2.annotations.Optional;

@Path("/file")
public class JerseyService 
{
	@POST
	@Path("/upload")
	@Consumes({MediaType.MULTIPART_FORM_DATA})
	public Response uploadPdfFile(  @FormDataParam("file") InputStream fileInputStream,
	                                @FormDataParam("file") FormDataContentDisposition fileMetaData,
	                                @QueryParam("filePath") String filePath) throws Exception
	{
		boolean debug = true;
	    String UPLOAD_PATH = filePath;
	    try
	    {
	        int read = 0;
	        byte[] bytes = new byte[1024];
	 
	        File theFile = new File(UPLOAD_PATH);
	        syso("Whether the file / dir exists " + theFile.exists(), debug);
//	        System.out.println("Whether the is Directory " + theFile.isDirectory());
	        syso("Making the directories"  + theFile.mkdirs(), debug);
	        syso("File upload path : " + UPLOAD_PATH, debug);
	        syso("File metadata file name : " + fileMetaData.getFileName(), debug);
	        OutputStream out = new FileOutputStream(new File(UPLOAD_PATH + "/" +fileMetaData.getFileName()));
	        System.out.println("Created file");
	        while ((read = fileInputStream.read(bytes)) != -1)
	        {
	            out.write(bytes, 0, read);
	        }
	        syso("Written file", debug);
	        out.flush();
	        out.close();
	        syso("Flushed and closed file", debug);
	    } catch (IOException e)
	    {
	        throw new WebApplicationException("Error while uploading file. Please try again !!");
	    }
	    return Response.ok("Data uploaded successfully !!").build();
	}
	
	
	
	@GET
	@Path("/get")
	@Produces("text/plain")
	public Response getFile(@QueryParam("filePath") String filePath, 
			@QueryParam("fileName") String fileName,
			@QueryParam("debug") @Optional boolean debug) {

		//String FILEPATH = "c:\\temp\\somefile.txt";
		File file = new File(filePath);

		syso("File Path received is : " + filePath, debug);
		
		syso("File Name received is : " + fileName, debug);
		
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
			"attachment; filename=\"" + fileName + "\"");
		syso("The response is : " + response.toString(), debug);
		return response.build();

	}
	
	@GET
	@Path("/getMP")
	@Produces(MediaType.MULTIPART_FORM_DATA)	
	public Response getFileMultiPart(@QueryParam("filePath") String filePath, 
			@QueryParam("fileName") String fileName,
			@QueryParam("debug") @Optional boolean debug) {

		File file = new File(filePath);

		syso("File Path received is : " + filePath, debug);
		
		syso("File Name received is : " + fileName, debug);
		
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
			"attachment; filename=\"" + fileName + "\"");
		syso("The response is : " + response.toString(), debug);
		return response.build();

	}
	
	@GET
	@Path("/getAOS")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)	
	public Response getFileAOS(@QueryParam("filePath") String filePath, 
			@QueryParam("fileName") String fileName,
			@QueryParam("debug") @Optional boolean debug) {

		File file = new File(filePath);

		syso("File Path received is : " + filePath, debug);
		
		syso("File Name received is : " + fileName, debug);
		
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
			"attachment; filename=\"" + fileName + "\"");
		syso("The response is : " + response.toString(), debug);
		return response.build();

	}
	
	@GET
	@Path("/getSample")
	@Produces("text/plain")
	public Response getDocFile() {

		String FILEPATH = "c:\\temp\\sample.txt";
		File file = new File(FILEPATH);

		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
			"attachment; filename=\"theNameIwant.log\"");
		return response.build();

	}
	
	private void syso(String message, boolean debug){
		if(debug){
			System.out.println(message);
		}
	}
	
	private void syso(String message){
		syso(message,true);
	}
	
	@POST
	@Path("/base")
	@Consumes({MediaType.MULTIPART_FORM_DATA})
	public Response uploadPdfFileWorking(  @FormDataParam("file") InputStream fileInputStream,
	                                @FormDataParam("file") FormDataContentDisposition fileMetaData) throws Exception
	{
		boolean debug = false;
	    String UPLOAD_PATH = "c:/temp/";
	    if(fileMetaData != null){
	    	syso("Metadata " + fileMetaData.toString(), debug);
	    }
	    try
	    {
	        int read = 0;
	        byte[] bytes = new byte[1024];

	        OutputStream out = new FileOutputStream(new File(UPLOAD_PATH + fileMetaData.getFileName()));

	        while ((read = fileInputStream.read(bytes)) != -1)
	        {
	            out.write(bytes, 0, read);
	        }

	        out.flush();
	        out.close();
	    } catch (IOException e)
	    {
	        throw new WebApplicationException("Error while uploading file. Please try again !!");
	    }
	    return Response.ok("Data uploaded successfully !!").build();
	}
}
