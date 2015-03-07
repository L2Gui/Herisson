package controller;

import model.Graph;

public interface IOAlgorithm {

	public Graph open(String filename) throws Exception;
	public void save(String filename, Graph graph);
	public boolean isConform(String filname);
}
