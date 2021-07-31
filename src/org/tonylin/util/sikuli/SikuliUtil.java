package org.tonylin.util.sikuli;

import java.awt.Rectangle;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.sikuli.api.ColorImageTarget;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

public class SikuliUtil {
	
	protected static boolean mDisplayDebugMode = false;
	protected static boolean mOperationDebugMode = false;
	private static DesktopMouse mMouse = new DesktopMouse();
	
	public static void enableOperationDebugMode(){
		mOperationDebugMode = true;
	}
	
	public static void disableOperationDebugMode(){
		mOperationDebugMode = false;
	}
	
	public static void enableDispalyDebugMode(){
		mDisplayDebugMode = true;
	}
	
	public static void disableDiplsayDebugMode(){
		mDisplayDebugMode = false;
	}
	
	public static void displayBox(ScreenRegion screenRegion){
		Canvas canvas = new DesktopCanvas();
		canvas.addBox(screenRegion).display(1);
	}

	public static String getResoluion(){
		ScreenRegion screenRegion = new DesktopScreenRegion();
		Rectangle bounds = screenRegion.getBounds();
		return (int)bounds.getWidth() + "x" + (int)bounds.getHeight();
	}
	
	public static void displayBoxForDebug(ScreenRegion screenRegion){
		if( mDisplayDebugMode )
			displayBox(screenRegion);
	}
	
	public static void clickRegionForDebug(ScreenRegion aRegion){
		if( mOperationDebugMode )
			return;
		clickRegion(aRegion);
	}
	
	public static void clickRegion(ScreenRegion aRegion){
		mMouse.click(aRegion.getCenter());
	}
	
	public static void clickImage(URL url) throws SikuliException {
		try {
			ScreenRegion findRegion = findImage(url, 3000);
			if( findRegion == null )
				throw new SikuliException("Can't find the target.");
			
			displayBoxForDebug(findRegion);
			clickRegionForDebug(findRegion);
		} catch( SikuliException e ){ 
			throw e;
		} catch( Exception e ){
			throw new SikuliException(e);
		}
	}
	
	public static void clickMultipleTargetImage(URL[] urls)throws SikuliException {
		ExpectOneTarget target = new ExpectOneTarget();
		
		for(int i = 0 ; i < urls.length ; i++) {
			target.addState( new ColorImageTarget(urls[i]), "url"+i);
		}
		
		ScreenRegion screenRegion = new DesktopScreenRegion();
		ScreenRegion findRegion =  screenRegion.wait(target, 3000);
		if( findRegion == null )
			throw new SikuliException("Can't find the target.");
		
		displayBoxForDebug(findRegion);
		clickRegionForDebug(findRegion);
	}
	
	public static ScreenRegion findImage(URL url, int waitTime){
		ScreenRegion screenRegion = new DesktopScreenRegion();
		Target target = new ColorImageTarget(url);
		return screenRegion.wait(target, waitTime);
	//	return null;
	}
	
	public static void clickImage(String aImagePath) throws SikuliException {
		try {
			clickImage(new File(aImagePath).toURI().toURL());
		} catch (MalformedURLException e) {
			throw new SikuliException(e);
		}
	}
}
