import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ProgramStanToRec {
	
	//	used to replace last occurence of the Xs in the NO-attribute of (nested) <NP>-tags
	public static String replaceLast(String string, String toReplace, String replacement) {
	    int pos = string.lastIndexOf(toReplace);
	    if (pos > -1) {
	        return string.substring(0, pos)
	             + replacement
	             + string.substring(pos + toReplace.length(), string.length());
	    } else {
	        return string;
	    }
	}

	public static void main(String[] args) {
    	
    	String output = "";
		
	    try {
	    	//	input file handler and DOM-parsing prerequisites
	    	File fileXML = new File("chapter1_adapted_withpost.xml");
	    	
	    	DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();    	
	    	
	    	Document bookDocument = docBuilder.parse(fileXML);
	    	bookDocument.getDocumentElement().normalize();
	    	
	    	//	pointer for important container node <sentences>
	    	Node sentencesNode = bookDocument.getElementsByTagName("sentences").item(0);
	    	Element sentencesElement = (Element) sentencesNode;	    	
	    	NodeList sentences = sentencesElement.getElementsByTagName("sentence");
	    	
	    	//	pointer for important container node <coreferences>
	    	Node coreferencesNode = bookDocument.getElementsByTagName("coreferences").item(0);
	    	Element coreferencesElement = (Element) coreferencesNode;	    	
	    	NodeList coreferences = coreferencesElement.getElementsByTagName("coreference");
	    	
	    	int maxTokenCount = 0;
	    	
	    	//	determine maxTokenCount by traversing the sentences
	    	for (int sentenceID = 1; sentenceID <= sentences.getLength(); sentenceID++) {
	    			    		
	    		Node sentenceNode = sentences.item(sentenceID - 1);
	    		Element sentenceElement = (Element) sentenceNode;
	    		
		    	NodeList tokens = sentenceElement.getElementsByTagName("token");
		    	
		    	if (tokens.getLength() > maxTokenCount)
		    		maxTokenCount = tokens.getLength();
	    	}	    	
	    	
	    	//	initialise arrays to simplify output generation; might not be ideal, but first idea, first try - worked	    	
	    	String [][] toInsertStart = new String[sentences.getLength()][maxTokenCount];
	    	String [][] toInsertEnd = new String[sentences.getLength()][maxTokenCount];
	    	
	    	ArrayList<Integer> mentionSizes = new ArrayList<Integer>();
	    	
	    	int mentionCount = 0;
	    	
	    	//	determine mention sizes by traversing the container node <coreferences>
	    	for (int coreferenceID = 1; coreferenceID <= coreferences.getLength(); coreferenceID++) {
	    		Node coreferenceNode = coreferences.item(coreferenceID - 1);
	    		Element coreferenceElement = (Element) coreferenceNode;
	    		
	    		NodeList mentions = coreferenceElement.getElementsByTagName("mention");
	    		
	    		mentionCount += mentions.getLength();
	    		
		    	//	... traversing mentions per container node <coreference>
		    	for (int mentionID = 1; mentionID <= mentions.getLength(); mentionID++) {
	
		    		Node mentionNode = mentions.item(mentionID - 1);	
		    		Element mentionElement = (Element) mentionNode;
		    		
		    		int mentStartNo = Integer.parseInt(mentionElement.getElementsByTagName("start").item(0).getTextContent());
					int mentEndNo = Integer.parseInt(mentionElement.getElementsByTagName("end").item(0).getTextContent());
					
					
					//	store mention size if it is a new one
					if(!mentionSizes.contains(mentEndNo - mentStartNo)) {
						mentionSizes.add(mentEndNo - mentStartNo);
					}
		    	}
	    	}
	    	
	    	//	sort occurring mention sizes
	    	Collections.sort(mentionSizes);	    	
	    	Integer [] mentionSizeArray = (Integer []) mentionSizes.toArray(new Integer[mentionSizes.size()]);
	    	
	    	int mentionsHandled = 0;
	    	
	    	//	extract coreference information, starting with smallest mention size
	    	for (int i = 0; i < mentionSizeArray.length; i++) {
	    		
	    		int currentMentionSize = mentionSizeArray[i];
	    		
		    	//	... traversing the container node <coreferences> for the current mention size
	    		for (int coreferenceID = 1; coreferenceID <= coreferences.getLength(); coreferenceID++) {	    		
		    		
		    		Node coreferenceNode = coreferences.item(coreferenceID - 1);
		    		Element coreferenceElement = (Element) coreferenceNode;
		    		
		    		NodeList mentions = coreferenceElement.getElementsByTagName("mention");
		    		
			    	//	... traversing mentions per container node <coreference>
			    	for (int mentionID = 1; mentionID <= mentions.getLength(); mentionID++) {
		
			    		Node mentionNode = mentions.item(mentionID - 1);	
			    		Element mentionElement = (Element) mentionNode;
			    		
			    		int mentSentID = Integer.parseInt(mentionElement.getElementsByTagName("sentence").item(0).getTextContent());
			    		int mentStartNo = Integer.parseInt(mentionElement.getElementsByTagName("start").item(0).getTextContent());
						int mentEndNo = Integer.parseInt(mentionElement.getElementsByTagName("end").item(0).getTextContent());
						
						//	save coreference information in arrays created to simplify output generation
						if(currentMentionSize == (mentEndNo - mentStartNo)) {
							mentionsHandled++;
							
							//	initialise array cell in toInsertStart if null
							if(toInsertStart[mentSentID - 1][mentStartNo - 1] == null) {
								//isInCorefStart[mentSentID - 1][mentStartNo - 1] = true;
								toInsertStart[mentSentID - 1][mentStartNo - 1] = "<NP NO=\"X\" CorefID=\"" 
										+ (9900 + coreferenceID) 
										+ "\">";    					
							}
							//	prepend to existing string, otherwise
							else {
								toInsertStart[mentSentID - 1][mentStartNo - 1] = "<NP NO=\"X\" CorefID=\"" 
										+ (9900 + coreferenceID) 
										+ "\">" + toInsertStart[mentSentID - 1][mentStartNo - 1];
							}
							
							//	initialise array cell in toInsertEnd if null
							if(toInsertEnd[mentSentID - 1][mentEndNo - 2] == null) {
								//isInCorefEnd[mentSentID - 1][mentEndNo - 2] = true;
								toInsertEnd[mentSentID - 1][mentEndNo - 2] = "</NP>";
							}
							//	append to existing string, otherwise
							else {
								//System.out.println("Uh oh, we've hit a snag!!");
								toInsertEnd[mentSentID - 1][mentEndNo - 2] += "</NP>";
							}
						}
			    	}
		    	}
	    	}
	    	
	    	//	used to handle replacement of ID values
	    	int [] replaced = new int[mentionCount];
	    	boolean [] boolReplaced = new boolean[coreferences.getLength()];
	    	int coreferencesHandled = 0;
	    	int replacer = 0;
	    	
	    	//	extract text by traversing the container node <sentences>, adding it to output
	    	for (int sentenceID = 1; sentenceID <= sentences.getLength(); sentenceID++) {
	    		
	    		Node sentenceNode = sentences.item(sentenceID - 1);
	    		Element sentenceElement = (Element) sentenceNode;
	    		
		    	NodeList tokens = sentenceElement.getElementsByTagName("token");
		    	
		    	//	... traversing tokens per container node <sentence>
		    	for (int tokenID = 1; tokenID <= tokens.getLength(); tokenID++) {
	
		    		Node tokenNode = tokens.item(tokenID - 1);	
		    		Element tokenElement = (Element) tokenNode;
		    		
		    		String toInsert = "";
		    		
		    		//	retrieve coreference info stored in toInsertStart if present and add it to output
		    		if(toInsertStart[sentenceID - 1][tokenID - 1] != null) {
		    			
		    			toInsert = toInsertStart[sentenceID - 1][tokenID - 1];
		    			
		    			Pattern patternCorefID = Pattern.compile("D=\"(\\d+)");
		    			Matcher matcherCorefID = patternCorefID.matcher(toInsert);
		    			
		    			//	handle replacement of IDs that still need to be set (NO="X")
		    			while (matcherCorefID.find()) {
		    				
		    				//	get current value of CorefID-attribute of current <NP>
		    				int replacee = Integer.parseInt(matcherCorefID.group(1).replace("D=\"", "")) - 9900;
		    				
		    				//	save information needed to replace CorefID-attribute values in order for the output to create Reconcile-conforming output
		    				if(!boolReplaced[replacee - 1]) {		    					
		    					replaced[replacer] = replacee;		    					
		    					boolReplaced[replacee - 1] = true;
		    					coreferencesHandled++;
		    				}
		    				//	replace last NO-attribute (ID) specified as "X" of current <NP>-start-tag-cluster element
			    			toInsert = replaceLast(toInsert, "X", ("" + replacer++));
			    			//toInsert = toInsert.replaceFirst("X", ("" + replacer++)); // used this alternative for testing
		    			}		    			
		    			output += toInsert;
		    			toInsert = "";		    			
		    		}
		    		
		    		//	add text of current token
		    		output += tokenElement.getElementsByTagName("word").item(0).getTextContent();
		    		
		    		//	retrieve coreference info stored in toInsertEnd if present and add it to output
		    		if(toInsertEnd[sentenceID - 1][tokenID - 1] != null) {
		    			output += toInsertEnd[sentenceID - 1][tokenID - 1];
		    		}
		    		
		    		output += " ";
		    	}
		    	//	remove trailing whitespace and add LF
		    	output = output.replaceAll("\\s+$", "") + "\n";
	    	}
	    	
	    	//	use stored replacement info and perform replacements
	    	for (int replacerCounter = 0; replacerCounter < replaced.length; replacerCounter++) {
	    		if(replaced[replacerCounter] > 0) {
	    			String find = "D=\"" + (replaced[replacerCounter] + 9900) + "\"";
	    			String replacement = "D=\"" + replacerCounter + "\"";
	    			
	    			output = output.replace(find, replacement);
	    		}
	    	}
	    	
	    	//	remove trailing whitespace and add LF
	    	output = output.replaceAll("\\s+$", "") + "\n";
	    	
	    	// save output to file
	    	Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("chapter1_adapted_withpost_xml.coref"), "utf-8"));
	    	writer.write(output);
	    	writer.close();
	    	
	    	//	generate output for testing purposes    	
	    	System.out.println("SentenceCount:\t\t" + sentences.getLength());
	    	System.out.println("MaxTokenCount:\t\t" + maxTokenCount + "\n");	    	
	    	
	    	System.out.println("CoreferenceCount:\t" + coreferences.getLength());
	    	System.out.println("CoreferencesHandled:\t" + coreferencesHandled + "\n");
	    	
	    	System.out.println("MentionCount:\t\t" + mentionCount);
	    	System.out.println("MentionsHandled:\t" + mentionsHandled + "\n");	    
	    	
	    	System.out.println("MentionSizes:\t\t" + mentionSizes.toString() + "\n");
	    }
	    catch (Exception e) {
	    	System.out.println("\n");
	    	e.printStackTrace();
	    }
	}
}