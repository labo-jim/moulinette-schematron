package labo.jim.sch;


import javax.xml.transform.Source;

import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmNode;

public class Moulinette {
	
	private static boolean outpoutEnabled = true;

	public static void main(String[] args) throws SaxonApiException  {
		
		SaxonHolder saxonH = SaxonHolder.getInstance();
		
		Source sampleSchematron = saxonH.sourceFromResources("samples/test-ISO.sch");
		Source sampleXML = saxonH.sourceFromResources("samples/test.xml");
		
		XdmNode compiledSch = compileSchematron(saxonH, sampleSchematron);
		
		XdmNode result = saxonH.runXslt(sampleXML, compiledSch.asSource());
		
		outputStep("SVRL Result", result);

	}
	
	private static XdmNode compileSchematron(SaxonHolder saxonH, Source schematron) throws SaxonApiException{
		XdmNode step1 = saxonH.runStep(schematron, SchematronStep.STEP1);
		outputStep("Skeleton STEP 1", step1);
		XdmNode step2 = saxonH.runStep(step1.asSource(), SchematronStep.STEP2);
		outputStep("Skeleton STEP 2", step2);
		XdmNode compiledSch = saxonH.runStep(step2.asSource(), SchematronStep.STEP3);
		outputStep("Skeleton STEP 3 : XSLT", compiledSch);
		return compiledSch;
	}
	
	private static void outputStep(String label, XdmNode step){
		if(outpoutEnabled){
			System.out.println(label + " :\n===================\n\n");
			System.out.println(step.toString() + "\n\n\n");
		}
			
	}

}
