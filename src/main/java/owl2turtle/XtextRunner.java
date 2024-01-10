package owl2turtle;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.generator.GeneratorContext;
import org.eclipse.xtext.generator.GeneratorDelegate;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.common.collect.Lists;
import com.google.inject.Injector;

import org.xtext.example.mydsl.RDFTurtleStandaloneSetup;
import org.xtext.example.mydsl.SparqlStandaloneSetup;

public class XtextRunner {
	
	public void runXtextModel(String ttl_file, String sp_file, String outpath) {
		Injector ttl_injector = new RDFTurtleStandaloneSetup().createInjectorAndDoEMFRegistration();
		Injector sp_injector = new SparqlStandaloneSetup().createInjectorAndDoEMFRegistration();
		
		ResourceSet ttl_rs = ttl_injector.getInstance(ResourceSet.class);
		ResourceSet sp_rs = sp_injector.getInstance(ResourceSet.class);
        ArrayList<Resource> resources = Lists.newArrayList();
        
        resources.add(ttl_rs.getResource(URI.createFileURI(ttl_file), true));
        resources.add(sp_rs.getResource(URI.createFileURI(sp_file), true));

//        IResourceValidator ttl_validator = ttl_injector.getInstance(IResourceValidator.class);
//        List<Issue> issues1 = ttl_validator.validate(resources.get(0), CheckMode.ALL, CancelIndicator.NullImpl);
//        for (Issue i : issues1) {
//            System.out.println(i);
//        }
//        
//        
//        IResourceValidator sp_validator = ttl_injector.getInstance(IResourceValidator.class);
//        List<Issue> issues2 = sp_validator.validate(resources.get(1), CheckMode.ALL, CancelIndicator.NullImpl);
//        for (Issue i : issues2) {
//            System.out.println(i);
//        }
//        

        GeneratorDelegate generator = ttl_injector.getInstance(GeneratorDelegate.class);
        
        
        JavaIoFileSystemAccess fsa = ttl_injector.getInstance(JavaIoFileSystemAccess.class);
        fsa.setOutputPath(outpath);
        
		GeneratorContext context = new GeneratorContext();
        context.setCancelIndicator(CancelIndicator.NullImpl);
        Resource ttl_r = resources.get(0);
        Resource sp_r = resources.get(1);
        ttl_r.getContents().addAll(sp_r.getContents());
        generator.generate(ttl_r, fsa, context);
        
	}
}
