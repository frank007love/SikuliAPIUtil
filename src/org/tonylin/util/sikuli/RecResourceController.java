package org.tonylin.util.sikuli;

import java.io.File;
import java.net.URL;

public class RecResourceController {

	private static RecResourceController mRC = null;
	
	private String DEFAULT_DIR = "default"; 
	private String RESOLUTION_DIR;
	
	private RecResourceController(int aWidth, int aHeight){
		RESOLUTION_DIR =  aWidth + "x" + aHeight;
	}
	
	public static RecResourceController getInstace(){
		if( mRC != null ){
			return mRC;	
		}
		return new RecResourceController(1024, 768);
	}
	
	public static RecResourceController getInstace(int aWidth, int aHeight){
		if( mRC == null ){
			mRC = new RecResourceController(aWidth, aHeight);
		}
		return mRC;
	}
	
	private String getResolutionPath(){
		return RESOLUTION_DIR + "/";
	}
	
	private String getDefaultPath(){
		return DEFAULT_DIR + "/";
	}
	
	private String getRelatedPath(String aRelatedPath){
		if( aRelatedPath.endsWith("/") )
			return aRelatedPath;
		return aRelatedPath + "/";
	}
	
	public URL getFileWithAutowire(String aFileName) throws Exception{
		URL url = getFileURL(aFileName, getResolutionPath());
		if( url != null ) {
			return url;
		}
		return getFileURL(aFileName);
	}
	
	public File getFile(String aFileName){	
		return new File(getDefaultPath() + aFileName);
	}
	
	public URL getFileURL(String aFileName) throws Exception{
		return this.getClass().getClassLoader().getResource(getDefaultPath() + aFileName);
	}
	
	public URL getFileURL(String aFileName, String aRelatedPath) throws Exception {
		return this.getClass().getClassLoader().getResource(getRelatedPath(aRelatedPath) + aFileName);
	}
}
