package add.coref.stanford;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import add.index.Coref;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class CorefAdder {

	public static void main(String[] args) {
		
		//	defining argument options 
		Option help = new Option("help", "print this message");
		Option pp = new Option("pp", "include post-processing for DCoref in the CoreNLP pipeline, ignored if -corenlp is not chosen (optional)");
		Option corenlp = new Option("corenlp", "run Stanford CoreNLP first (optional)");
		Option folder	 = OptionBuilder.withArgName("src-folder")
										.hasArg()
            							.withDescription("use given folder as source (required)" )
            							.create("folder");		
		
		Options options = new Options();
		options.addOption(help);
		options.addOption(pp);
		options.addOption(corenlp);
		options.addOption(folder);
		
		//	instantiating CLI parser
		BasicParser parserCLI = new BasicParser();
		
		try {
			CommandLine commandLine = parserCLI.parse( options, args);
			boolean doPostproc = false;
			/*	accept defined options only, show help text instead; 
			 *	folder is required, corenlp is optional
			 */	
            if ( commandLine.hasOption("folder") ) {
            	File dataFolder = new File(commandLine.getOptionValue("folder"));
                if (commandLine.hasOption("corenlp")) {
                	if (commandLine.hasOption("pp")) {
                		doPostproc = true;
                		runCoreNLP(dataFolder, doPostproc);
                		adaptXML(dataFolder, doPostproc);
                	}
                	else {
                		runCoreNLP(dataFolder, doPostproc);
                		adaptXML(dataFolder, doPostproc);
                	}
                }
                else {
                	if (commandLine.hasOption("pp")) {
                		System.out.println("INFORMATION: Ignoring command line option -pp\nPlease, refer to the usage information with the option -help.\n---");
                	}
                	adaptXML(dataFolder, doPostproc);
                }
                
                long start = System.nanoTime();
                System.out.print("Creating index for " + dataFolder.getName() + " ... ");
                
    			String postproc = "";
    			if (doPostproc) {
    				postproc = System.getProperty("file.separator") + "post-processing";
    			}
                
                //	prepare index-adder input
                File extractedChapterFilesFolder = new File(dataFolder.getAbsolutePath() 
                		+ System.getProperty("file.separator") 
                		+ "output" + System.getProperty("file.separator") 
                		+ "chapters" + System.getProperty("file.separator") 
                		+ "extracted"
                		+ postproc);
                
                System.out.println(extractedChapterFilesFolder.getAbsolutePath());
                
                File[] listOfChapterFiles = extractedChapterFilesFolder.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        String name = pathname.getName().toLowerCase();
                        return name.endsWith(".xml") && pathname.isFile();
                    }
                });
                String[] indexAdderInput = new String[listOfChapterFiles.length + 3];
                          
                //	add paths for input files
                indexAdderInput[0] = "-i";
                
                for (int i = 0; i < listOfChapterFiles.length; i ++) {
                	if (listOfChapterFiles[i].isFile()) {
                		indexAdderInput[i+1] = listOfChapterFiles[i].getAbsolutePath();
                	}
                }
                
                //	add path for output file
                indexAdderInput[indexAdderInput.length - 2] = "-o";
    			String indexFilename = "index.xml";
    			if (doPostproc) {
    				indexFilename = "index_pp.xml";
    			}                
                indexAdderInput[indexAdderInput.length - 1] = (dataFolder.getAbsolutePath() + "" + System.getProperty("file.separator") + "output" + System.getProperty("file.separator") + indexFilename);
                
            	//	run index-adder
                Coref.main(indexAdderInput);
                long elapsedNanoseconds = System.nanoTime() - start;
    	    	System.out.println("Complete! [" + ((double) Math.round((elapsedNanoseconds / 1000000000.0) * 1000) / 1000) + " sec]"); 
            }
            else {
                HelpFormatter helpFormatter = new HelpFormatter();
                helpFormatter.printHelp("arguments for coref-adder.jar", options);
            }
		}
		catch (UnrecognizedOptionException e) {
			System.out.println("Invalid option for coref-adder.jar! Please see the usage notes below and try again.\n");
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("possible arguments for coref-adder.jar:", options);
		}
		catch (Exception e) {
			System.out.println("Aborted! The following error occured:");
	    	e.printStackTrace();
		}
	}
	
	private static void runCoreNLP(File dataFolder, boolean doPostproc) {
		try {
			long start = System.nanoTime();
			System.out.print("Running Stanford CoreNLP for "+ dataFolder.getName() + " ... ");
			
			String postproc = "";
			
			//	configure properties for pipeline of the Stanford CoreNLP
			Properties properties = new Properties();
			properties.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
			if (doPostproc) {
				properties.setProperty("dcoref.postprocessing", "true");
				postproc = System.getProperty("file.separator") + "post-processing";
				System.out.print("with post-processing ... ");
			}
			System.out.println();
			
			/*	instantiate pipeline of the Stanford CoreNLP with the settings above;
			 * 	redirect configuration output of Stanford to logfile
			 */
			System.out.print("... configuring pipeline ... ");
			PrintStream oldErrStream = System.err;			
			System.setErr(new PrintStream(new FileOutputStream(dataFolder.getAbsolutePath() + System.getProperty("file.separator") + "output" + System.getProperty("file.separator") + "StanfordCoreNLP.log")));
			
			StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);
			
			//	reset output stream
			System.setErr(oldErrStream);
	    	long elapsedNanoseconds = System.nanoTime() - start;
	    	System.out.println("Done! [" + ((double) Math.round((elapsedNanoseconds / 1000000000.0) * 1000) / 1000) + " sec]"); 
			
			//	get file handlers and prepare folder structure for the annotation files
			File chapterFolder = new File(dataFolder.getAbsolutePath() + System.getProperty("file.separator") + "output" + System.getProperty("file.separator") + "chapters");
			File[] listOfChapterFiles = chapterFolder.listFiles();
			File annotationsDirectory = new File(dataFolder.getAbsolutePath() + "" + System.getProperty("file.separator") + "output" + System.getProperty("file.separator") + "chapters" + System.getProperty("file.separator") + "annotated" + postproc);
			annotationsDirectory.mkdir();
			
			String fileName = "";
			String chapterText = "";
			OutputStreamWriter xmlOutput = null;
			
			//	annotate files one by one and save output as xml
			for (File file : listOfChapterFiles) {
				if (file.isFile()) {
					long startPerChapter = System.nanoTime();
					
					//	get the name (without filename extension) and the content of the current file
			    	fileName = file.getName().replaceFirst("[.][^.]+$", "");			    	
			    	chapterText = new String(Files.readAllBytes(Paths.get(file.getPath())), StandardCharsets.UTF_8);
			    	
			    	System.out.print("... annotating \"" + fileName + ".txt\" ... ");
			    	Annotation annotation = new Annotation (chapterText);
			    	pipeline.annotate(annotation);
			    	
			    	System.out.print("saving ... ");
			    	xmlOutput = new OutputStreamWriter(
			    			new FileOutputStream(
			    					annotationsDirectory.getAbsolutePath()  
			    						+ System.getProperty("file.separator") + fileName 
		    							+ ".xml"), "utf-8");
			    	pipeline.xmlPrint(annotation, xmlOutput);
			    	
	    			long elapsedNanosecondsPerChapter = System.nanoTime() - startPerChapter;
	    			System.out.println("Done! [" + ((double) Math.round((elapsedNanosecondsPerChapter / 1000000000.0) * 1000) / 1000) + " sec]");
				}
			}
			elapsedNanoseconds = System.nanoTime() - start;
	    	System.out.println("Complete! [" + ((double) Math.round((elapsedNanoseconds / 1000000000.0) * 1000) / 1000) + " sec]"); 
		}
		catch (Exception e) {
			System.out.println("Aborted! Please, check the logfile \"" + dataFolder.getAbsolutePath() + System.getProperty("file.separator") + "output" + System.getProperty("file.separator") + "StanfordCoreNLP.log\".");
	    	e.printStackTrace();
		}
	}
	
	private static void adaptXML(File dataFolder, boolean doPostproc) {
		
	    try {
	    	long start = System.nanoTime();	    	
	    	System.out.println("Adapting XML-files for " + dataFolder.getName() + " ... ");
	    	
			String postproc = "";
			if (doPostproc) {
				postproc = System.getProperty("file.separator") + "post-processing";
			}
	    	
	    	/*	use this file to retrieve <chapter>-tags; they are used as root elements for 
	    	 * 	the XML files for the individual chapters; prepare the folder structure for the
	    	 * 	files created in the following
	    	 */
	    	File fileXML = new File(dataFolder.getAbsolutePath() + System.getProperty("file.separator") + "output" + System.getProperty("file.separator") + dataFolder.getName() + "_zwischen.xml");	    	
			File adaptedAnnotationsDirectory = new File(dataFolder.getAbsolutePath() 
					+ System.getProperty("file.separator") + "output" 
					+ System.getProperty("file.separator") + "chapters" 
					+ System.getProperty("file.separator") + "extracted"
					+ postproc);
			adaptedAnnotationsDirectory.mkdir();
			
	    	//	DOM-parse and normalize (removes whitespace that might lead to problems) the file described above
	    	DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();    	
	    	Document bookDocument = docBuilder.parse(fileXML);
	    	bookDocument.getDocumentElement().normalize();	 
	    	
	    	NodeList chapters = bookDocument.getElementsByTagName("chapter");
	    	
	    	/*	traverse the individual <chapter>-tags; 
	    	 *	clone the current node, delete its text content;
	    	 *	retrieve <sentences>-node from annotation file;
	    	 *	retrieve enclosing <coreference>-node from annotation file, adapt it
	    	 *	to suit the requirements of the subsequent group;
	    	 *	use the clone of the current <chapter>-node and add the <sentences>- and 
	    	 *	the <coreference>-node to create a new XML document;
	    	 *	save the new XML document;
	    	 */
	    	for (int chapterID = 1; chapterID <= chapters.getLength(); chapterID++) {
	    		//	clone the current node, delete its text content;
	    		Node clonedChapterNode = chapters.item(chapterID - 1).cloneNode(true);	    			
    			clonedChapterNode.setTextContent("");
    					
    	    	System.out.print("... processing chapter" + chapterID + ".xml ... ");
    	    	long startPerChapter = System.nanoTime(); 
    	    	
    	    	//	retrieve, DOM-parse, and normalize annotation file for current chapter
    			File annotatedChapterFile = new File(dataFolder.getAbsolutePath() 
    					+ System.getProperty("file.separator") + "output" 
    					+ System.getProperty("file.separator") + "chapters" 
    					+ System.getProperty("file.separator") + "annotated"
    					+ postproc
    					+ System.getProperty("file.separator") + "chapter"
    					+ chapterID + ".xml");	    			
    	    	Document annotatedChapter = docBuilder.parse(annotatedChapterFile);
    	    	annotatedChapter.getDocumentElement().normalize();	    
    	    	
    	    	//	retrieve <sentences>-tag and <coreference>-tag from annotation file
    	    	Node sentencesNode = annotatedChapter.getElementsByTagName("sentences").item(0);
    	    	Node coreferencesNode = annotatedChapter.getElementsByTagName("coreference").item(0);  	
    	    	
    	    	/*	clone and adopt retrieved nodes in order to create a new, well-formed XML file
    	    	 * 	append <sentences>-node without any changes
    	    	 */
    			Document extractedChapter = docBuilder.newDocument();					
    			extractedChapter.appendChild(extractedChapter.adoptNode(clonedChapterNode));
				extractedChapter.getDocumentElement().appendChild(extractedChapter.adoptNode(sentencesNode.cloneNode(true)));				
				Node adoptedClonedCoreferencesNode = extractedChapter.adoptNode(coreferencesNode.cloneNode(true));
				
				/*	renaming <coreference>-node (the tag enclosing the actual, individual
				 * 	<coreference>-nodes is also named <coreference>) to <coreferences>
				 */
				extractedChapter.renameNode(adoptedClonedCoreferencesNode, adoptedClonedCoreferencesNode.getNamespaceURI(), "coreferences");
				
				NodeList coreferenceNodes = ((Element)adoptedClonedCoreferencesNode).getElementsByTagName("coreference");
				
				//	add IDs to individual <coreference>-tags inside <coreferences>-node
		    	for (int j = 0; j < coreferenceNodes.getLength(); j++) { 
		    		Element coreferenceElement = (Element) coreferenceNodes.item(j);	    			
	    			coreferenceElement.setAttribute("id", "" + (j+1));
		    	}
		    	
		    	//	append the adapted <coreferences>-node
				extractedChapter.getDocumentElement().appendChild(adoptedClonedCoreferencesNode);
				
				//	create the new XML file
				System.out.print("saving ... ");
    			TransformerFactory transformerFactory = TransformerFactory.newInstance();	    			
    			Transformer transformer = transformerFactory.newTransformer();
    			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    			
    			DOMSource source = new DOMSource(extractedChapter);
    			StreamResult result = new StreamResult(
    					new OutputStreamWriter(
    							new FileOutputStream(
    									dataFolder.getAbsolutePath() 
    										+ System.getProperty("file.separator") + "output" 
											+ System.getProperty("file.separator") + "chapters" 
    										+ System.getProperty("file.separator") + "extracted"
    										+ postproc
											+ System.getProperty("file.separator") + dataFolder.getName() + "_chapter" 
    										+ chapterID 
    										+ ".xml"), "utf-8"));
    	 
    			transformer.transform(source, result);
    			
    			long elapsedNanosecondsPerChapter = System.nanoTime() - startPerChapter;
    			System.out.println("Done! [" + ((double) Math.round((elapsedNanosecondsPerChapter / 1000000000.0) * 1000) / 1000) + " sec]");
    		}
	    	long elapsedNanoseconds = System.nanoTime() - start;
	    	System.out.println("Complete! [" + ((double) Math.round((elapsedNanoseconds / 1000000000.0) * 1000) / 1000) + " sec]"); 
        }
	    catch (Exception e) {	    	
	    	System.out.println("Aborted! The following error occured:");
	    	e.printStackTrace();
        }
	}
}
