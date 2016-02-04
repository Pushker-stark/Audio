/***************************************************************************
*                                                                          *                     
* Panako - acoustic fingerprinting                                         *   
* Copyright (C) 2014 - Joren Six / IPEM                                    *   
*                                                                          *
* This program is free software: you can redistribute it and/or modify     *
* it under the terms of the GNU Affero General Public License as           *
* published by the Free Software Foundation, either version 3 of the       *
* License, or (at your option) any later version.                          *
*                                                                          *
* This program is distributed in the hope that it will be useful,          *
* but WITHOUT ANY WARRANTY; without even the implied warranty of           *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the            *
* GNU Affero General Public License for more details.                      *
*                                                                          *
* You should have received a copy of the GNU Affero General Public License *
* along with this program.  If not, see <http://www.gnu.org/licenses/>     *
*                                                                          *
****************************************************************************
*    ______   ________   ___   __    ________   ___   ___   ______         *
*   /_____/\ /_______/\ /__/\ /__/\ /_______/\ /___/\/__/\ /_____/\        *      
*   \:::_ \ \\::: _  \ \\::\_\\  \ \\::: _  \ \\::.\ \\ \ \\:::_ \ \       *   
*    \:(_) \ \\::(_)  \ \\:. `-\  \ \\::(_)  \ \\:: \/_) \ \\:\ \ \ \      * 
*     \: ___\/ \:: __  \ \\:. _    \ \\:: __  \ \\:. __  ( ( \:\ \ \ \     * 
*      \ \ \    \:.\ \  \ \\. \`-\  \ \\:.\ \  \ \\: \ )  \ \ \:\_\ \ \    * 
*       \_\/     \__\/\__\/ \__\/ \__\/ \__\/\__\/ \__\/\__\/  \_____\/    *
*                                                                          *
****************************************************************************
*                                                                          *
*                              Panako                                      * 
*                       Acoustic Fingerprinting                            *
*                                                                          *
****************************************************************************/



package be.panako.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Properties;

/**
 * Writes and read the configuration values to and from a properties file.
 * 
 * @author Joren Six
 */
public class Config {
	
	/**
	 * The file on disk that is used to store the configuration values. 
	 * On Android this can be stored here: res/raw/
	 */
	private final String configurationFileName;
	
	/**
	 * The values are stored here, in memory.
	 */
	private final HashMap<Key,String> configurationStore;
	
	/**
	 * Hidden default constructor. Reads the configured values, or stores the defaults. 
	 */
	public Config() {
		//if on android
		if(onAndroid()){
			configurationFileName = new File("/sdcard/config.properties").getAbsolutePath();
		}else{
			String path = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String decodedPath = "";
			try {
				decodedPath = URLDecoder.decode(path, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			configurationFileName = new File(new File(decodedPath).getParent(),"config.properties").getAbsolutePath();
		}		
		configurationStore = new HashMap<Key, String>();
		if(!FileUtils.exists(configurationFileName)){
			writeDefaultConfiguration();
		}
		readConfiguration();
	}
	
	/**
	 * Read configuration from properties file on disk.
	 */
	private void readConfiguration() {
		Properties prop = new Properties();
    	try {
            //Loads a properties file.
    		FileInputStream inputstream = new FileInputStream(configurationFileName);
    		prop.load(inputstream);
    		inputstream.close();
    		for(Key key : Key.values()){
				String configuredValue = prop.getProperty(key.name());
				configurationStore.put(key, configuredValue);				
			}
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
	}

	private void writeDefaultConfiguration(){
		Properties prop = new Properties();
		try {
			//Set the default properties value.
			for(Key key : Key.values()){
				prop.setProperty(key.name(), key.defaultValue);
			}
			//Save the properties to the configuration file.
			prop.store(new FileOutputStream(configurationFileName), null);
		} catch (IOException ex) {
			ex.printStackTrace();
	    }
	}
	
	private static Config instance;
	public static Config getInstance(){
		if(instance == null){
			instance =new Config();
		}
		return instance;
	}
	
	public static String get(Key key){
		//re read configuration
		//getInstance().readConfiguration();
		HashMap<Key,String> store = getInstance().configurationStore;
		final String value;
		if(store.get(key)!=null){
			value = store.get(key).trim();
		}else{
			value = key.getDefaultValue();
		}
		return value;
	}
	
	public static int getInt(Key key){
		return Integer.parseInt(get(key));
	}
	
	public static float getFloat(Key key){
		return Float.parseFloat(get(key));
	}
	
	public static boolean getBoolean(Key key){
		return get(key).equalsIgnoreCase("true");
	}
	
	/**
	 * Check if Panako is running on Android.
	 * @return True if the virtual machine is Dalvik.
	 */
	public boolean onAndroid(){
		return System.getProperty("java.vm.name").equalsIgnoreCase("Dalvik");
	}
	
	public static void set(Key key, String value) {
		HashMap<Key,String> store = getInstance().configurationStore;
		store.put(key, value);
	}
}
