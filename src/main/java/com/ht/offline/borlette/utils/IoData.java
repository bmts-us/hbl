/*
 * IoData.java
 *
 * Created on October 25, 2004, 12:24 PM
 */

package com.ht.offline.borlette.utils;

/**
 *
 * @author  hvertus
 */
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.*;

public class IoData {
    
    /** Creates a new instance of ioData */
    public IoData() {
    }

    //check if a file exist, by passing the full path with the filename
    public static boolean fileExist(String filename) {

		boolean fileexist = false;
		try {
			File datafile = new File(filename);

			if (datafile.exists()) { // if file exists
				fileexist = true;
			}
		} catch (Exception ioe) {
			System.out.println("File not found exception : " + ioe);
		}

		return fileexist;

    } //end of method

    //check if a file exist
    public boolean fileExist(String dirname,String filename) throws IOException {

       boolean ok = false ;
       
       File datadir = new File(dirname);
       
       try {                      
           
          if (datadir.isDirectory()) { //si c'est un repertoire                                  
              File datafile = new File(datadir,filename);
              
              if (datafile.exists()) {       //si le fichier existe                         
                 ok = true;
              }	      
          }
	  	  
       }
       catch (Exception ioe) {
           System.out.println("Incapable de creer le fichier : " + ioe);
       }
        
       return ok;
       
    } //end of method
    
    public static boolean isFileEmpty(String string) {
	    File file = new File(string);
        return file.length() == 0;
    }
    
    //check if a string is a file or a directory
    public static boolean isAFile(String string) {
       File file = new File(string);
        //si c'est un fichier
        return file.isFile();

    } //end of method    
    
    //method to clear a file. Provide the file with the full path. 
    public static void clearFile(String fileName) {
    	try {
		    PrintWriter writer = new PrintWriter(new File(fileName));
		    writer.print("");
		    writer.close();   
    	} catch (Exception ioe) {
	        System.out.println("Incapable de verifier le fichier :"+ioe);
	    }    
    }
    
    //check if a file exits
    public boolean checkFile(String dirname,String filename) throws IOException {
       boolean ok   = false ;
       File datadir = new File(dirname);       
       try {                      
          if (datadir.isDirectory()) { //si c'est un repertoire                                  
              File datafile = new File(datadir,filename);
              ok = datafile.exists();  //si le fichier existe
          }	  	  
       }
       catch (Exception ioe) {
           System.out.println("Incapable de verifier le fichier :"+ioe);
       }
        
       return ok;       
    } //end of method

    public static boolean directoryExist(String dirname) {
        boolean exist   = false ;
        File dir = new File(dirname);
        try {                                 
           if (dir.exists()) { //si le repertoire existe
        	   exist = true;
           }  
        } catch (Exception e) {
            System.out.println("Unable to verify if the directory exists :" + e.getMessage());
        }       
        return exist;
     }
    
  //create directory 
    public static boolean createDirectory(String dirname) {
        boolean create   = false ;
        File dir = new File(dirname);
        try {                                 
           if (!dir.exists()) { //si le repertoire n'existe pas, creer le       
               dir.mkdir();
               create = true;
           }  
        } catch (Exception e) {
            System.out.println("Incapable de creer le repertoire :" + e.getMessage());
        }       
        return create;
     }    
    
  //create directory and sub directories
    public static boolean createDirectories(String dirname) {
       boolean create   = false ;
       File dir = new File(dirname);
       try {                                 
          if (!dir.exists()) { //si le repertoire n'existe pas, creer le       
              dir.mkdirs(); //directory and sub directories
              create = true;
          }  
       } catch (Exception e) {
           System.out.println("Incapable de creer le repertoire :" + e.getMessage());
       }       
       return create;
    }

    //creer le fichier en fournissant le repertoire complet.
    public static boolean createFile(String filename) {
       boolean created   = false ;
       try {                                 
              File datafile = new File(filename);
              
              if (!datafile.exists()) {       //si le fichier n'existe pas       
                  datafile.createNewFile(); //creer le fichier
                  created = true;
              } else created = false;
       }
       catch (Exception ioe) {
           System.out.println("Incapable de creer le fichier :"+ioe);
       }
        
       return created;       
    } //end of method
    
    public static boolean createFile(String dirname,String filename) {
       boolean ok   = false ;
       File datadir = new File(dirname);
       
       try {                                 
          if (!datadir.exists()) datadir.mkdir();  //si le repertoire n'existe pas, creer le

          if (datadir.isDirectory()) { //si c'est un repertoire                                  
              File datafile = new File(datadir,filename);
              
              if (!datafile.exists()) {       //si le fichier n'existe pas       
                  datafile.createNewFile(); //creer le fichier
                  ok = true;
              } else ok = false;
          }	  	  
       }
       catch (Exception ioe) {
           System.out.println("Incapable de creer le fichier :"+ioe);
       }
        
       return ok;       
    } //end of method
    
    //method to rename a file
    public static boolean renameFile(String oldFileName,String newFileName) {        
    	File oldfile = new File(oldFileName);
		File newfile = new File(newFileName);

        return oldfile.renameTo(newfile);
    }
    
    //method to save a file as another
    public static void saveFileAs(String source, String target) {
    	InputStream inStream = null;
    	OutputStream outStream = null;
 
    	try{
 
    	    File sourceFile =new File(source);
    	    File targetFile =new File(target);
 
    	    inStream = new FileInputStream(sourceFile);
    	    outStream = new FileOutputStream(targetFile);
 
    	    byte[] buffer = new byte[1024];
 
    	    int length;
    	    
    	    //copy the file content in bytes 
    	    while ((length = inStream.read(buffer)) > 0) {
    	    	outStream.write(buffer, 0, length);
    	    }
 
    	    inStream.close();
    	    outStream.close();
 
    	    //System.out.println("File is copied successful!");
    	} catch(IOException e){
    		e.printStackTrace();
    	} 	    	
    }
        
    //method to Write an array in a file
    static public void writeToFile(String dirname,String filename,String[] data) {
                
       File datadir = new File(dirname);
       
       try {                      
           
          if (!datadir.exists()){ //si le repertoire n'existe pas
              datadir.mkdir();    // creer le
          }

          if (datadir.isDirectory()) { //si c'est un repertoire                 
                 
              File datafile = new File(datadir,filename);
              
              if (!datafile.exists()) {       //si le fichier n'existe pas       
                  datafile.createNewFile(); //creer le fichier
              }
                        
              BufferedWriter out = new BufferedWriter(new FileWriter(datafile));
              
                for(int i=0; i < data.length; i++) {

		    String output = data[i] + "\n";

		    out.write(output);
                }
              
              
              out.close();
              
          }
                        
       }
       catch (IOException e) {
           
       }
    
    } // end of method
    
    static public void put(String dirname,String filename,String[] data) throws IOException {
        writeToFile(dirname,filename,data); //calling writeToFile above
    }
    
    //method to write a string in a file
    static public void writeToFile(String fileName, String data) {

       try {                          
    	   File dataFile = new File(fileName);
              
           if (!dataFile.exists()) {     //if the file doesn't exist       
        	   dataFile.createNewFile(); //create the file                  
           }
               
           //Read and write into the file
           RandomAccessFile raf = new RandomAccessFile(dataFile, "rw");              
              
           //Seek to end of file
           raf.seek(dataFile.length());

           //Append to the end              
           raf.writeBytes(data + "\n");
           
           raf.close();  //close the file                                    
       } catch (IOException ioe) {
           ioe.printStackTrace();
       }    
    } // end of method    
    
    //method to write a string in a file
    static public void writeToFile(String dirname,String filename,String data) {
                
       File datadir = new File(dirname);
       
       try {                      
           
          if (!datadir.exists()){ //si le repertoire n'existe pas
              datadir.mkdir();    // creer le
          }

          if (datadir.isDirectory()) { //si c'est un repertoire                 
                 
              File datafile = new File(datadir,filename);
              
              if (!datafile.exists()) {       //si le fichier n'existe pas       
                  datafile.createNewFile(); //creer le fichier                  
              }
               
              //lire et ecrire dans le fichier
              RandomAccessFile raf = new RandomAccessFile(datafile, "rw");              
              
              // Seek to end of file
              raf.seek(datafile.length());
    
              String datas = data + "\n" ; //ligne suivante
              
              // Append to the end              
              raf.writeBytes(datas);
              raf.close();              
              
          }
                        
       }
       catch (IOException e) {
           
       }
    
    } // end of method
    
    static public void put(String dirname,String filename,String data) throws IOException {
        writeToFile(dirname,filename,data); //calling writeToFile above
    }

    
    static public void writeArrayToFile(String filepath, boolean appendable, String[] data){

	try {

			FileWriter outputFile = new FileWriter( filepath, appendable);
	
	        for(int i=0; i < data.length; i++) {
	
			    String output = data[i] + "\n";
	
			    outputFile.write(output);
	        }
	
			outputFile.close();

        }
        catch( Exception e) { 
            System.err.println(e); 
        }
    
    } // end of method

    //method to Write an object to a file by passing the full path with the filename
    public static void writeObjectToFile(String filename, Object objName) {

       try {
           File datafile = new File(filename);

           if (!datafile.exists()) {       //if the file doesn't exist
               datafile.createNewFile(); //create it
           }

           //create the object ouput stream
           ObjectOutputStream objectOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(datafile)));
           //write the object to the file
           objectOut.writeObject(objName);
           //close the output stream
           objectOut.close();
       } catch (IOException ex) {
           System.err.println(ex);
       }

    } // end of method
    
    //method to Write an object to a file
    static public void writeObjectToFile(String dirname,String filename,Object objName) {
                
       File datadir = new File(dirname);
       
       try {                      
           
          if (!datadir.exists()){ //si le repertoire n'existe pas
              datadir.mkdir();    // creer le
          }

          if (datadir.isDirectory()) { //si c'est un repertoire                 
                 
              File datafile = new File(datadir,filename);
              
              if (!datafile.exists()) {       //si le fichier n'existe pas       
                  datafile.createNewFile(); //creer le fichier
              }
    
              //create the object ouput stream
              ObjectOutputStream objectOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(datafile)));              
              //write the object to the file
              objectOut.writeObject(objName);
              //close the output stream
              objectOut.close();  
          }
                        
       }
       catch (IOException ex) {
           System.err.println(ex);
       }       
    
    } // end of method 


    //method to add an object in a file
    static public void put(String dirname,String filename,Object objName) throws IOException {
        writeObjectToFile(dirname,filename, objName); //calling writeObjectToFile above
    }

    //method to add an object in a file by passing the full path with the filename
    static public void put(String filename,Object objName) throws IOException {
        writeObjectToFile(filename, objName); //calling writeObjectToFile above
    }

    //method to read an object from a file by passing the full path with the filename
    static public Object readObjectFromFile(String filename) {

       Object obj = null;

       try {
              File datafile = new File(filename);

              if (datafile.exists()) {
	              //create the object input stream
	              ObjectInputStream objectIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream(datafile)));
	              //read the object from the file
	              obj = objectIn.readObject();
	              //close the input stream
	              objectIn.close();
              }
       }
       catch (IOException ex) {
           System.err.println(ex);
       }
       catch (ClassNotFoundException ex) {
           System.err.println(ex);
       }

       return obj;

    } // end of method

    //method to read an object from a file
    static public Object readObjectFromFile(String dirname,String filename) throws IOException {
                
       File datadir = new File(dirname);
       Object objName = null;
       
       try {                      

          if (datadir.isDirectory()) { //si c'est un repertoire                 
                 
              File datafile = new File(datadir,filename);
              
              if (!datafile.exists()) 
                  throw new FileNotFoundException();      //si le fichier n'existe pas capturer une exception     
                          
              //create the object input stream
              ObjectInputStream objectIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream(datafile)));
              //read the object from the file
              objName = objectIn.readObject();
              //close the input stream
              objectIn.close();                        
          }
          else throw new FileNotFoundException(); //si le repertoire n'existe pas capturer une exception
                        
       }
       catch (IOException ex) {
           System.err.println(ex);
       }
       catch (ClassNotFoundException ex) {
           System.err.println(ex);
       }       
       
       return objName;
    
    } // end of method    
    
//    //method to read an array object from a file
//    public Object[] readArrayObjectFromFile(String dirname,String filename) throws IOException {
//                
//       File datadir = new File(dirname);
//       Object[] objName = null;
//       int sizeObject = 0;
//       
//       try {                      
//
//          if (datadir.isDirectory()) { //si c'est un repertoire                 
//                 
//              File datafile = new File(datadir,filename);
//              
//              if (!datafile.exists()) 
//                  throw new FileNotFoundException();      //si le fichier n'existe pas capturer une exception     
//              
//              sizeObject = (int)datafile.length(); //number of object in the file
//              
//              //lire dans le fichier
//              RandomAccessFile raf = new RandomAccessFile(datafile, "r");              
//              
//              //if (sizeObject == 0)  //nothing in the file
//              raf.seek(0);       // Seek to begin of file (BOF)
//              
//              ObjectInputStream objectIn = null;
//              
//              for (int i=0; i < sizeObject;i++) {
//                raf.seek(i);
//                //create the object input stream
//                objectIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream(datafile)));
//                //read the object from the file
//                objName[i] = (Object)objectIn.readObject();
//              }
//              
//              //close the input stream
//              objectIn.close(); 
//          }
//          else throw new FileNotFoundException(); //si le repertoire n'existe pas capturer une exception
//                        
//       }
//       catch (IOException ex) {
//           System.err.println(ex);
//       }
//       catch (ClassNotFoundException ex) {
//           System.err.println(ex);
//       }       
//       
//       return objName;
//    
//    } // end of method    
//    
//    public int sizeOfFile(String dirname,String filename) throws IOException {
//       File datadir = new File(dirname);
//       int size_file = 0;
//       
//       try {                      
//
//          if (datadir.isDirectory()) { //si c'est un repertoire                 
//                 
//              File datafile = new File(datadir,filename);
//              
//              if (!datafile.exists()) 
//                  throw new FileNotFoundException();      //si le fichier n'existe pas capturer une exception     
//              
//              size_file = (int)datafile.length(); //number of object in the file
//          }
//          else throw new FileNotFoundException(); //si le repertoire n'existe pas capturer une exception
//          
//       }
//       catch (IOException ex) {
//           System.err.println(ex);
//       } 
//       return size_file;
//    }

    
    /*read to a file by line
     *@filename : file to read the lines
     *call this method like that : new  project.utils.ioData().readLinesFromFile("dalton.data")
     */
     public static String readLinesFromFile(String filename) {

		String line = "";
	
		try {
			FileInputStream fis=new FileInputStream(filename);
			int i;
	
			do {
				i=fis.read();
				line+=(char)i;
			} while(i!=-1);
			//System.out.println(str);
			fis.close();
	    } catch(IOException ioe) {
	        System.out.println(ioe);
		}
	
		return line; //return the lines
    }
     
    /*read to a file by line
     *@filename : file to read the lines
     *call this method like that : new  project.utils.ioData().loadFile("dalton.data")
     *return the lines to an ArrayList
     *
     *Example to use this method
     
     * ArrayList array = loadFile("dalton.data");
        for (int i=0; i<array.size(); i++)
            System.out.println(i+1 + ":\t" + array.get(i));
     */     
    @SuppressWarnings({ "rawtypes", "resource", "unchecked" })
	public ArrayList loadFile(String fileName)  { //public static
        
        if ((fileName == null) || (fileName == ""))
            throw new IllegalArgumentException();

        String line;
        ArrayList file = new ArrayList();

        try
        {
            BufferedReader in = new BufferedReader(new FileReader(fileName));

            if (!in.ready())
                throw new IOException();

            while ((line = in.readLine()) != null)
                file.add(line);

            in.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
//            return null;
        }

        return file;
    }

    //this array function will read a text file, then put each line of the file into an array of string. Provide the full path of the file 
	public static String[] readFromFile(String dataFile) {
		String line = null;
		String[] stringFileLines = null;
	
		try {                    
             RandomAccessFile raFile = new RandomAccessFile(dataFile, "rw");
             
             while(( line = raFile.readLine( )) != null ) { 
            	 stringFileLines = Utils.addToStringArray(stringFileLines, line);
             }
                
             raFile.close();
        } catch( Exception e ) { 
            System.err.println(e); 
        }

        return Utils.isNotNull(stringFileLines) ? stringFileLines.length > 0 ? stringFileLines : null : null;
    } //end of method       
    
    
    //method to write an integer value in a file
    public void writeInt(String dirname,String filename,int value) throws IOException {
        
        try {
                File f = new File(dirname,filename);
                RandomAccessFile raf = new RandomAccessFile(f, "rw");
    
                // Seek to begin of file
                raf.seek(0);
                
                // Append to the begin                
                raf.writeInt(value);
                raf.close();
        } 
        catch (IOException e) {
        }
        
    } //end of method

    //method to read an integer value from a file
    public int readInt(String dirname,String filename) throws IOException {
        
        int value = 0 ;
        
        try {
                File f = new File(dirname,filename);
                RandomAccessFile raf = new RandomAccessFile(f, "rw");
    
                // Seek to begin of file
                raf.seek(0);
                
                if (raf.length()<=0) {
                  raf.writeInt(0);   
                  raf.seek(0); // Re-Seek to begin of file
                }
                    
                // Append to the begin                
                value = raf.readInt();
                
                raf.close();
        } 
        catch (IOException e) {
        }

        return value ;
    } //end of method


    //debug dans un fichier
    static public void writeToLogFile(String dirname,String filename,String data) throws IOException {
                
       File datadir = new File(dirname);
       
       try {                      
           
          if (!datadir.exists()){ //si le repertoire n'existe pas
              datadir.mkdir();    // creer le
          }

          if (datadir.isDirectory()) { //si c'est un repertoire                 
                 
              File datafile = new File(datadir,filename);
              
              if (!datafile.exists()) {       //si le fichier n'existe pas       
                  datafile.createNewFile(); //creer le fichier
              }
               
              //lire et ecrire dans le fichier
              RandomAccessFile raf = new RandomAccessFile(datafile, "rw");              
              
              // Seek to end of file
              raf.seek(datafile.length());
    
              String datas = data + "\n" ; //ligne suivante
              
              // Append to the end              
              raf.writeBytes(datas);
              raf.close();              
              
          }
                        
       }
       catch (IOException e) {
           
       }
    
    } // end of method
        
//*******************************************************************
    
    //debug 
    static public void Debugging(String dirname,String filename,String[] data) throws IOException {
                
       File datadir = new File(dirname);
       
       try {                      
           
          if (!datadir.exists()){ //si le repertoire n'existe pas
              datadir.mkdir();    // creer le
          }

          if (datadir.isDirectory()) { //si c'est un repertoire                 
                 
              File datafile = new File(datadir,filename);
              
              if (!datafile.exists()) {       //si le fichier n'existe pas       
                  datafile.createNewFile(); //creer le fichier
              }
               
              //lire et ecrire dans le fichier
              RandomAccessFile raf = new RandomAccessFile(datafile, "rw");              
              
              // Seek to end of file
              raf.seek(datafile.length());
    
                for(int i=0; i < data.length; i++) {

		    String output = data[i] + "\n";
                    
                    // Append to the end              
                    raf.writeBytes(output);
                }
              
              raf.close();              
              
          }
                        
       }
       catch (IOException e) {
           
       }
    
    } // end of method
    
    
    //method to delete a file in a specific directory
    static public void deleteFile(String dirname,String filename) throws IOException {                
       File datadir = new File(dirname);
          if (datadir.isDirectory()) { //si c'est un repertoire                 
                 
              File datafile = new File(datadir,filename);
              
              if (datafile.exists())  //si le fichier existe
                  datafile.delete(); //supprimer le fichier                                                           
          }
    
    } // end of method

    //method to delete a file by passing the full path with the filename
    static public void deleteFile(String filename) {         
         try {        	 
	         if(fileExist(filename)) {   
	        	 new File(filename).delete(); //delete the file
	        	// System.out.println("<>----------- File should be deleted :: "+filename);
	         }
         } catch(Exception ioe) {
        	 
         }
    } // end of method

    //method to delete a file , same as deleteFile
    static public void kill(String dirname,String filename) throws IOException {
        deleteFile(dirname,filename);
    } // end of method

    //method to delete a file by passing the full path with the filename
    static public void kill(String filename) throws IOException {
        deleteFile(filename);
    } // end of method


    /*This method get the filename in a given full path
	  for exemple C:\\dalton\\pator\\encrypt.class or C:/dalton/pator/encrypt.class
	  /dalton/pator/dalton.JPG
	  return encrypt.class or dalton.jpg	  
	 */	
    public String getFile(String filename) {

		String file=null;
	    int i = filename.lastIndexOf('\\');	    
	    int j = filename.lastIndexOf('/');

		if(filename != null) {		    
		    if(i>0 && i<filename.length()-1) {
				 file = filename.substring(i+1); //.toLowerCase();
	    	}
	    	else {
                if(j>0 && j<filename.length()-1) {
				   file = filename.substring(j+1); //.toLowerCase();
                }
            }
		}

		return file;

    } // end of method

    /*This method get only the extension of a given file
	  for example : encrypt.class return "class"
	 */  
    public String getExtensionOfFile(String filename) {
	if(filename != null) {
	    int i = filename.lastIndexOf('.');
	    if(i>0 && i<filename.length()-1) {
		return filename.substring(i+1).toLowerCase();
	    }
    }
	return null;
    } // end of method

    /*This method get only the filename without the extension
	  for example : encrypt.class return "encrypt"
	 */      
    public String getFileWithoutExtension(String filename) {
		String file=null;

	    int posDot = filename.indexOf('.');
	    if(posDot==-1){
                file = filename;
            }
            else{
                file = filename.substring(0, posDot);
            }

	   return file;
    } // end of method
    
    //Cette methode definit la taille d'un repertoire
    public int dirSize(String dirName) {
        File dir = new File (dirName);
        File [] contents = dir.listFiles();        
        return contents.length;         
    }
    
    //Trouver seulement la liste de tous les fichiers contenant dans un repertoire
    //@dirName : complet name of the directory
    public static String[] getFiles(String dirName) {
        File dir = new File (dirName);
        File [] contents = dir.listFiles();        
        String[] files = null;

        if (contents!=null) {
            files = new String[contents.length];
            for (int i = 0;i < contents.length; i ++) {
                if (contents[i].isFile()) { //we don't need the directories
                   files[i] = contents[i].getName().trim(); //put all files in the array
                }
            }
        }

        return files;
    }
    
    /*function to get date and time of a file
      @file: full path of the file
      @dateTimeFormat: date format and time format, ex:yyyy-MM-dd hh:mm:ss a  
      */
    public static String fileDateTime(String file, String dateTimeFormat) {
        File f = new File(file);        
        SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);      
        return sdf.format(f.lastModified());    	
    }

    /*//Affiche la liste de tous les fichiers contenant dans des repertoires
    //@dirsName : complet name of the directories
    public String[] displayFiles(String[] dirsName) {
		String[] files = new String[]{};//null;
		int count = 0;
		for(int i=0; i < dirsName.length; i++) {
			File dir = new File (dirsName[i]);
			File[] contents = dir.listFiles(); 
			count++;
			if (contents!=null) {
				//files = new String[contents.length];
				for (int j = 0;j < contents.length; j++) {
					if (contents[j].isFile()) { //we don't need the directories
						files[j] = contents[j].getName().trim(); //put all files in the array
					}
				}
			}
		}

        return files;
    }*/

    //Affiche la liste de tous les fichiers et repertoires contenant dans un repertoire donne
    //@dirName : complet name of the directory
    public String[] displayFilesAndFolders(String dirName) {
        File dir = new File (dirName);
        File [] contents = dir.listFiles();
        String[] files = new String[dirSize(dirName)] ;

        if (contents != null) {
            for (int i = 0;i < dirSize(dirName); i ++) {
                files[i] = contents[i].getName();
            }
        }

//       File datadir = new File(dirname);
//           if (datadir.isDirectory()) { //si c'est un repertoire
//              File datafile = new File(datadir,filename);

        return files;
    }

    //Affiche la liste de tous les fichiers contenant dans un repertoire sans leurs extension
    //@dirName : complet name of the directory    
    public String[] displayFilesWithoutExtension(String dirName) {
        File dir = new File (dirName);
        File [] contents = dir.listFiles();        
        String[] val = new String[dirSize(dirName)] ;

        if (contents != null) {
            for (int i = 0;i < dirSize(dirName); i ++) {
                val[i] = getFileWithoutExtension(contents[i].getName());
            }
        }

        return val;
    }    
    
    /*//method to get all files in a given folder
    public List getAllFilesInFolder(String dirName) {
        String [] files = displayFiles(dirName);
        List allfiles = Arrays.asList(files);   
        // Sort
        Collections.sort(allfiles);
               
       return  allfiles;
    }*/
    
    //method to get all files in a given folder without their extension
    public List<String> getAllFilesInFolderWithoutExtension(String dirName) {
        String [] files = displayFilesWithoutExtension(dirName);
        List<String> allfiles = Arrays.asList(files);   
        // Sort
        Collections.sort(allfiles);
               
       return  allfiles;
    }
    
    /**
     * Converts a file into a byte[] array.
     * @param file file to be converted
     * @return file converted to {@code byte[]}
     * @throws IOException
     */
     @SuppressWarnings("resource")
	public static byte[] getBytesFromFile(File file) {
         byte[] data = null;
         try {
            InputStream is = new FileInputStream(file);

            // Get the size of the file
            long length = file.length();

            if (length > Integer.MAX_VALUE) {
                /* File is too large. If we are trying to determine the length of the byte[]
                * array, it can only return an int, so we are limited by the file size.
                */
                throw new IOException(file.getName() + " is too large.");
            }

            // Create the byte array to hold the data
            data = new byte[(int) length];

            // Read in the bytes
            int offset = 0;
            int bytesRead = 0;
            while (offset < data.length && (bytesRead = is.read(data, offset, data.length - offset)) >= 0) {
                offset += bytesRead;
            }

            // Ensure all the bytes have been read in
            if (offset < data.length) {
                throw new IOException("An exception occurred while trying to read + " + file.getName() + " into the byte array.");
            }

            // Close the input stream and return bytes
            is.close();
         } catch(Exception exp){}
         
        return data;
     }
             
     /*method to zip some files
      *@zipfileName : name of the zip file
      *@filestozip  : array of files to zip 
      *(Example : String[] filestozip = new String[]{"C:/Files/file1.txt", "C:/Files/file2.txt", "C:/Files/file3.txt"};
      */
     public static void zip(String zipfileName, String[] filestozip) {
    	byte[] buf = new byte[1024];

    	try {
                // Create the ZIP file
       		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfileName));
       		// Compress the files
       		for (int i=0; i<filestozip.length; i++) {
                    FileInputStream in = new FileInputStream(filestozip[i]);
                    // Add ZIP entry to output stream.
                    out.putNextEntry(new ZipEntry(filestozip[i]));
                    // Transfer bytes from the file to the ZIP file
                    int len;
                    while ((len = in.read(buf)) > 0) {
                	out.write(buf, 0, len);
                    }
                    // Complete the entry
                    out.closeEntry();
                    in.close();
        	}
        	// Complete the ZIP file
        	out.close();
    	} catch (IOException e) {}
     }
     
     /*method to unzip a zip file
      *@zipfileName : name of the zip file
      *@unzipto : folder to unzip the zip file
      *(Example : IoData.unzip("C:/tmp/test/files/myArchives.zip","C:/tmp/test/files/unzip/");
      *leave blank the @unzipto to unzip in the same folder where the zip file is (IoData.unzip("C:/tmp/test/files/myArchives.zip","");)
      */
     public static void unzip(String zipfileName, String unzipto) {
		 int BUFFER = 1024;
          try {
               BufferedOutputStream out = null;
               ZipInputStream  in = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipfileName)));
               ZipEntry entry;
               while((entry = in.getNextEntry()) != null) {
                    int count;
                    byte[] data = new byte[BUFFER];

                    // write the files to the disk
                    out = new BufferedOutputStream(new FileOutputStream(unzipto + "/" + entry.getName()),BUFFER);

                    while ((count = in.read(data,0,BUFFER)) != -1) {
                         out.write(data,0,count);
                    }
                    out.close();
               }
               in.close();
               out.flush();
               out.close();
          }
          catch(Exception e) { e.printStackTrace(); }
     }
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
}
