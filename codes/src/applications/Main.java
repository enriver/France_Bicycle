/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package applications;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

import tools.JenaEngine;

/**
 * @author DO.ITSUDPARIS
 */
public class Main {
	/**
	 * @param args
	 *            the command line arguments
	 * @return 
	 */
	public static Object[] main(String query_filename) {
		String NS = "";
		// lire le model a partir d'une ontologie
		String Rennes_bikes = "data/cities/rennes_bikes.rdf";
		
		
		Model model = JenaEngine.readModel(query_filename);
		if (model != null) {
			//lire le Namespace de l'ontologie
			NS = model.getNsPrefixURI("");
			// modifier le model
			// Ajouter une nouvelle femme dans le modele: Nora, 50, estFilleDe Peter
			// JenaEngine.createInstanceOfClass(model, NS, "Female", "Nora");
			//  JenaEngine.updateValueOfDataTypeProperty(model, NS, "Nora", "age", 50);
			// JenaEngine.updateValueOfObjectProperty(model, NS, "Nora", "isDaughterOf", "Peter");
			
			
			
			// Just add data to work with
			//JenaEngine.createInstanceOfClass(model, NS, "station", "5510");
			//JenaEngine.updateValueOfDataTypeProperty(model, NS, "5510", "available_bikes", "5");
			//JenaEngine.updateValueOfDataTypeProperty(model, NS, "5510", "available_free_spots", "19");
			//JenaEngine.updateValueOfDataTypeProperty(model, NS, "5510", "total_spots", "24");
			//JenaEngine.updateValueOfDataTypeProperty(model, NS, "5510", "lastupdate", "2020-03-11T15:14:10+00:00");
			
			//JenaEngine.createInstanceOfClass(model, NS, "station", "5516");
			//JenaEngine.updateValueOfDataTypeProperty(model, NS, "5516", "available_bikes", "9");
			//JenaEngine.updateValueOfDataTypeProperty(model, NS, "5516", "available_free_spots", "7");
			//JenaEngine.updateValueOfDataTypeProperty(model, NS, "5516", "total_spots", "16");
			//JenaEngine.updateValueOfDataTypeProperty(model, NS, "5516", "lastupdate", "2020-03-11T15:14:10+00:00");

			
			//apply owl rules on the model
			Model owlInferencedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/owlrules.txt");
			// apply our rules on the owlInferencedModel
			Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(owlInferencedModel, "data/rules.txt");
			// query on the model after inference
			//System.out.println(JenaEngine.executeQueryFile(inferedModel,
			//		"data/query.txt"));
			
			
			
			String query_return = JenaEngine.executeQueryFile(inferedModel, "data/query.txt");
			System.out.println(query_return);
	
			String[] a = (query_return.split("\n"));
			String[] headers = a[1].split("\\|");
			
			ArrayList<String> head = new ArrayList<String>();
			for(int i=1;i<headers.length-1;i++) {
				head.add(headers[i]);
			}
			
			List<List<String>> body = new ArrayList<List<String>>();
			for(int i=3;i<a.length-1;i++) {
				String[] b = a[i].split("\\|");
				
				ArrayList<String> bodyparts = new ArrayList<String>();
				
				for(int j=1;j<b.length-1;j++) {
					bodyparts.add(b[j]);
				}
				body.add(bodyparts);
			
			}
			
			//List<List<List<String>>> head_body = new ArrayList<List<List<String>>>();
			Object[] head_body = new Object[3];
			
			// Make list out of these arraylist	
			String[] head_1 = new String[head.size()];
			String[][] body_1 = new String[body.size()][head.size()];
			
			for(int i=0;i<head.size();i++) {
				head_1[i] = head.get(i);
				
				for(int j=0;j<body.size();j++) {
					body_1[j][i] = body.get(j).get(i).split("\\^\\^")[0];
				}
			}
			
			head_body[0] = (head_1);
			head_body[1] = (body_1);
			head_body[2] = query_return;
			
			return head_body;
			
		} else {
			System.out.println("Error when reading model from ontology");
		}
		return null;
	}
	


}
