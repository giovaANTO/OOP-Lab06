package it.unibo.oop.lab06.generics1;

import java.util.*;


public class GraphImpl<N> implements Graph<N> {
	
	private final Map<N, Set<N>> graphMapper;

	
	public GraphImpl(Map<N, Set<N>> map) {
		this.graphMapper = map;
	}
	
	public GraphImpl() {
		this(new HashMap<>());
	}
	
	/**
	 * Add a node in the graph
	 * @param node to add in the graph
	 */
	public void addNode(N node) {
		if(node != null && !this.graphMapper.containsKey(node)) {
			this.graphMapper.put(node, new HashSet<>());
		}
	}

	/**
	 * Add an edge in the graph
	 * @param source node in the graph
	 * @param target node in the graph
	 */
	public void addEdge(N source, N target) {
		if(source != null && target != null) {
			this.addNode(source);
			this.graphMapper.get(source).add(target);
		}
	}

	/**
	 * Return the node set of the graph
	 * @return the node set of the graph
	 */
	public Set<N> nodeSet() {
		return this.graphMapper.keySet();
	}
	
	/**
	 * Return all the linked nodes of a source
	 * @return the set of nodes linked to the source
	 */
	public Set<N> linkedNodes(N node) {
		return  this.graphMapper.getOrDefault(node, null);
	}
	
	/**
	 * Return the path between two nodes using the DFS Algorithm
	 * @param source node in the graph
	 * @param target node in the graph
	 * @return the path from source to target
	 */
	public List<N> getPath(N source, N target) {
		List<N> outputList = new LinkedList<>();
		findUtility(source, target, (LinkedList<N>) outputList, new HashMap<>());
		return outputList;
	}

	private void findUtility(N node, N target, LinkedList<N> list, Map<N, Boolean> exploredNodes) {
		
		exploredNodes.put(node, true);
		if(!list.contains(target)) {
			list.add(node);
		}
		if(node == target) {
			return;
		}
		Iterator<N> iterator = this.graphMapper.get(node).iterator();
		while(iterator.hasNext()) {
			N adjacentEdge = iterator.next();
			if(!exploredNodes.getOrDefault(adjacentEdge,false)) {
				findUtility(adjacentEdge, target, list, exploredNodes);
			}
		}
		if(!list.contains(target)) {
			list.removeLast();
		}
	}
	
	
}
