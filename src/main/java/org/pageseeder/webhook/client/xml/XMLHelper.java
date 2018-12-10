/*
 * Copyright (c) 2018 Allette systems pty. ltd.
 */
package org.pageseeder.webhook.client.xml;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * The Class XMLHelper.
 *
 * @author Carlos Cabral
 * @since 05 Dec. 2018
 */
public class XMLHelper {

  
  /**
   * Parses the specified file using the given handler.
   *
   * @param handler  The content handler to use.
   * @param xmlBody the xml body
   * @throws SAXException the SAX exception
   * @throws ParserConfigurationException the parser configuration exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void parse(ContentHandler handler, String xmlBody) throws SAXException, ParserConfigurationException, IOException {
    StringReader reader = new StringReader(xmlBody);
    
    //Sax factory
    SAXParserFactory factory = SAXParserFactory.newInstance();      
    factory.setValidating(false);
    factory.setNamespaceAware(false);

    //Sax parser
    SAXParser parser = factory.newSAXParser();

    // get the reader
    XMLReader xmlreader = parser.getXMLReader();
    xmlreader.setContentHandler(handler);
    xmlreader.parse(new InputSource(reader));
  }
}
