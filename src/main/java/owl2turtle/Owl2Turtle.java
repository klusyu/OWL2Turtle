package owl2turtle;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.*;

import java.io.File;

public class Owl2Turtle {
    public static void main(String[] args) {
    	if (args.length < 1) {
    		System.out.println("Please specific a command `convert` or `xtext`");
    		return;
    	}
    	String cmd = args[0];
    	if (cmd.equals("convert")) {
    		System.out.println("Run Convert");
    		String inputFilePath = args.length > 1 ? args[1] : "input.owl";
            String outputFilePath = args.length > 2 ? args[2] : "output/output.turtle";
            Convert(inputFilePath, outputFilePath);
    	}
    	else if (cmd.equals("xtext")) {
    		System.out.println("Run Xtext");
    		String ttlFilePath = args.length > 1 ? args[1] : "input.turtle";
            String sparqlFilePath = args.length > 2 ? args[2] : "input.sparql";
            String outpath = args.length > 3 ? args[3] : "output";
            RunXtext(ttlFilePath, sparqlFilePath, outpath);
    	}
    	else {
    		System.out.println("Incorrect command.");
    		System.out.println("Please specific a command `convert` or `xtext`");
    	}
        
    }
    
    protected static void Convert(String inputFilePath, String outputFilePath) {
        try {

            File inputFile = new File(inputFilePath);
            
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLOntology ontology = manager.loadOntologyFromOntologyDocument(inputFile);

            TurtleDocumentFormat turtleFormat = new TurtleDocumentFormat();
            manager.saveOntology(ontology, turtleFormat, IRI.create(new File(outputFilePath).toURI()));

            System.out.println("Ontology was successfully converted to Turtle format.");
            

            
        } catch (OWLOntologyCreationException | OWLOntologyStorageException e) {
            e.printStackTrace();
        }
    }
    
    
    protected static void RunXtext(String ttlFilePath, String sparqlFilePath, String outpath) {
        XtextRunner runner = new XtextRunner();
        runner.runXtextModel(ttlFilePath, sparqlFilePath, outpath);
        System.out.println("Xtext Done!");
    }
}


