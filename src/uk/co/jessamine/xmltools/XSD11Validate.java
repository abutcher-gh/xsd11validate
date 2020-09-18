package uk.co.jessamine.xmltools;

import java.lang.reflect.*;
import java.io.File;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXParseException;

public class XSD11Validate {
  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Usage: xsd11validate schema.xsd [file.xml...]");
      System.exit(1);
    }
    System.setProperty("javax.xml.validation.SchemaFactory:http://www.w3.org/XML/XMLSchema/v1.1",
           "org.apache.xerces.jaxp.validation.XMLSchema11Factory");

    int errors = 0;
    String xsdFileName = args[0];
    try {
      Class<?> c = Class.forName("org.apache.xerces.jaxp.validation.XMLSchema11Factory");
      Constructor<?> ctor = c.getConstructor();
      //SchemaFactory factory = (SchemaFactory) ctor.newInstance();
      SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/XML/XMLSchema/v1.1");
      Schema schema = factory.newSchema(new File(xsdFileName));
      Validator validator = schema.newValidator();
      for (int i = 1; i < args.length; ++i) {
        String xmlFileName = args[i];
        try
        {
          Source source = new StreamSource(new File(xmlFileName));
          validator.validate(source);
        }
        catch (SAXParseException ex)
        {
          System.err.println(xmlFileName + ":" + ex.getLineNumber() + ":" + ex.getColumnNumber() + ": " + ex.getMessage());
          ++errors;
        }
        catch (Exception ex)
        {
          System.err.println(xmlFileName + ": " + ex.getMessage().trim().replaceAll("\\r\\n|\\r|\\n", "\n" + xmlFileName + ": "));
          ++errors;
        }
      }
    }
    catch (Exception ex) {
      System.err.println(xsdFileName + ": Failed schema compilation: " + ex.getMessage());
      ++errors;
    }
    System.exit(errors);
  }
}
