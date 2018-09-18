package labo.jim.sch;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmDestination;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;

public class SaxonHolder {
	
	private static SaxonHolder instance;
	
	private Processor proc;
	private XsltCompiler compiler;
	private DocumentBuilder docBuilder;
	
	private Map<SchematronStep, XsltTransformer> transformers;
	private SchematronLoader schematronLoader;

	


	public static SaxonHolder getInstance(){
		if(instance == null) instance = new SaxonHolder();
		return instance;
	}
	
	private SaxonHolder(){
		proc = new Processor(false);
		docBuilder = proc.newDocumentBuilder();
		compiler = proc.newXsltCompiler();
		transformers = new EnumMap<>(SchematronStep.class);
		schematronLoader = new SchematronLoader();
	}
	
	
	// =========================================
	
	public Source sourceFromResources(String path){
		URL url = SaxonHolder.class.getClassLoader().getResource(path);
		try {
			return new StreamSource(new File(url.toURI()));
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public XdmNode documentFromResources(String path) throws SaxonApiException{
		return docBuilder.build(sourceFromResources(path));
	}
	
	// ==============================================
	
	public XdmNode runXslt(Source source, XsltTransformer xsl) throws SaxonApiException{
		xsl.setSource(source);
		XdmDestination destination = new XdmDestination();
		xsl.setDestination(destination);
		xsl.transform();
		
		return destination.getXdmNode();
	}
	
	public XdmNode runXslt(Source source, Source xslt) throws SaxonApiException{	
		XsltTransformer xsl = compiler.compile(xslt).load();
		return runXslt(source, xsl);
	}
	
	
	public XdmNode runStep(Source source, SchematronStep step) throws SaxonApiException{
		XsltTransformer xsl = schematronTransformer(step);
		return runXslt(source, xsl);
	}
	
	
	public XsltTransformer schematronTransformer(SchematronStep step) throws SaxonApiException{
		if(transformers.get(step) == null){
			XsltExecutable executable = compiler.compile(new StreamSource(schematronLoader.getStepAsFile(step)));
			transformers.put(step, executable.load());
		}
		return transformers.get(step);
	}
	

}
