import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class State {
    private int id;
    private String color;
    private String typeN;

    public State(int pId, String pColor, String ptypeN) {
        this.setId(pId);
        this.setColor(pColor);
        if (ptypeN == null) {
            this.setTypeN("");
        } else {
            this.setTypeN(ptypeN);
        }
    }

    public String getTypeN() {
        return typeN;
    }

    public void setTypeN(String typeN) {
        this.typeN = typeN;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        State tmp = (State) o;
        if (this.id != tmp.id) {
            return false;
        }
        return true;
    }
}

class Transition {
    private State source;
    private State target;
    private String label;
    private String color;

    public Transition(State pSource, State pTarget, String pLbl, String pColor) {
        this.setSource(pSource);
        this.setTarget(pTarget);
        this.setLabel(pLbl);
        this.setColor(pColor);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public State getTarget() {
        return target;
    }

    public void setTarget(State target) {
        this.target = target;
    }

    public State getSource() {
        return source;
    }

    public void setSource(State source) {
        this.source = source;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

// class LTS {
// private Map<State, List<Transition>> mapLTS;

// public LTS(Map<State, List<Transition>> pMapLTS) {
// this.mapLTS = new HashMap<State, List<Transition>>();
// }

// public Map<State, List<Transition>> getMapLTS() {
// return mapLTS;
// }
// }

class Cluster {
    private static int incrementId = 1;
    private int id;
    private int numberOfStates;
    private String neighType;
    private List<State> neighborhoods;
    private List<String> commonLabels;
    private List<String> allLabels;

    public Cluster(int pNumberOfStates, String pNeighType, List<State> pN,
            List<String> pCommonLabels, List<String> pAllLabels) {
        this.setId(incrementId++);
        this.setNumberOfStates(pNumberOfStates);
        this.setNeighType(pNeighType);
        this.setNeighborhoods(pN);
        this.setCommonLabels(pCommonLabels);
        this.setAllLabels(pAllLabels);
    }

    public List<String> getCommonLabels() {
        return commonLabels;
    }

    public void setCommonLabels(List<String> commonLabels) {
        this.commonLabels = commonLabels;
    }

    public List<String> getAllLabels() {
        return allLabels;
    }

    public void setAllLabels(List<String> allLabels) {
        this.allLabels = allLabels;
    }

    public String getNeighType() {
        return neighType;
    }

    public void setNeighType(String neighType) {
        this.neighType = neighType;
    }

    public int getNumberOfStates() {
        return numberOfStates;
    }

    public void setNumberOfStates(int numberOfStates) {
        this.numberOfStates = numberOfStates;
    }

    public List<State> getNeighborhoods() {
        return neighborhoods;
    }

    public void setNeighborhoods(List<State> neighborhoods) {
        this.neighborhoods = neighborhoods;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}



public class newLTS { 
    static String getStateColor (int state) {
        String stateColor = "";
        if(state == 0) {
            stateColor = "RED";
        } else if (state < 0) {
            stateColor = "GREEN";
        } else {
            stateColor = "BLUE";
        }
        return stateColor;
    }

    static void printN(Map<Integer, String> mapN) {
        for (Map.Entry mapElement : mapN.entrySet()) {           
            int tmpId = (int) mapElement.getKey();
            String tmpN = mapN.get(tmpId);
            System.out.println(tmpId + " : " + tmpN);
        }
    }

    static void printLTS(Map<State, List<Transition>> lts) {
        int count = 0;
        for (Map.Entry mapElement : lts.entrySet()) {           
            State tmpState = (State)mapElement.getKey();
            List<Transition> tmpListTrans = lts.get(tmpState);
            System.out.println("State[" + tmpState.getId() + "] :");
            if(!tmpState.getTypeN().equals("")) {System.out.println("Is Neighborhood : " + tmpState.getTypeN());}
            if(tmpListTrans.size() > 0) {
                for (Transition trans : tmpListTrans) {
                    count++;
                    System.out.println(">> Source : " 
                    + trans.getSource().getId() + ", Label : "
                    + trans.getLabel() + " ("
                    + trans.getColor() + "), Target : "
                    + trans.getTarget().getId());
                } 
            } else {
                System.out.println("Empty (No Transitions)");
            }
            System.out.println();
        } 
        System.out.println("Number of Trans : " + count);
    }

    // static int countClusters(Map <State, List<Transition>> lts, State state, List <Integer> tmpListVisited) {
    //     List <Transition> transitions = lts.get(state);
    //     if(!state.getTypeN().equals("") && !tmpListVisited.contains(state.getId())) {
    //         System.out.println("ID = " + state.getId() + ", N = " + state.getTypeN());
    //         tmpListVisited.add(state.getId());
    //         return 1;
    //     } else if(transitions.size() == 0) {
    //         return 0;
    //     } else {
    //         int tmp = 0;
    //         for (Transition transition : transitions) {
    //             tmp += countClusters(lts, transition.getTarget(), tmpListVisited);
    //         }
    //         return tmp;
    //     }
    // }

    static void traverseCluster(Map <State, List<Transition>> lts, State state, State stateOne,
	    List <Integer> tmpListVisitedInCluster, List <State> tmpListVisitedStates,
	    List <State> tmpStates, List <String> commonLabels, List <String> allLabels, List <State> newClusterStates) {
    	
    	// Check loop
    	if(tmpListVisitedInCluster.contains(state.getId())) {
    		return;
    	}
    	
    	// Check common label
    	if(!state.getTypeN().equals("") && state.getTypeN().equals(stateOne.getTypeN())) {
    		List <String> tmpLabels = new ArrayList<String>();
	    	for (Transition stateLabels : lts.get(state)) {
				tmpLabels.add(stateLabels.getLabel());
			}
	    	commonLabels.retainAll(tmpLabels);
	    	tmpLabels.removeAll(allLabels);
	    	allLabels.addAll(tmpLabels);
    	}
    	
    	// If this state is a normal state or (a state with the same type as the first Faulty State and has common label)
        if(state.getTypeN().equals("") || (state.getTypeN().equals(stateOne.getTypeN()) && commonLabels.size()!=0)) {
            
        	tmpListVisitedInCluster.add(state.getId());

        	// If it's a Faulty State then we add it to the list of Faulty States
            if(!state.getTypeN().equals("") && !tmpListVisitedStates.contains(state)) {
                tmpStates.add(state);
            } 
            
            // Add to the list of visited states (for the whole LTS)
            if(!tmpListVisitedStates.contains(state)) {
                tmpListVisitedStates.add(state);
            }
            
            // Traverse to the next state in the cluster
            List <Transition> transitions = lts.get(state);
            if (transitions.size() != 0) {
            	List <Integer> tmpListVisitedInClusterNext =  new ArrayList<Integer>();
            	tmpListVisitedInClusterNext.addAll(tmpListVisitedInCluster);
                for (Transition transition : transitions) {
                	traverseCluster(lts, transition.getTarget(), stateOne, tmpListVisitedInClusterNext,
	                        tmpListVisitedStates, tmpStates, commonLabels, allLabels, newClusterStates);
                }
            }
        } else if (!state.getTypeN().equals(stateOne.getTypeN()) && !tmpListVisitedStates.contains(state) ) {
            // If we find another cluster then we add to the list of clusters in a cluster
        	newClusterStates.add(state);
        }
    }
    
    // Function to call when we traversing the LTS (not yet in a cluster)
    static void traverseLTS(Map <State, List<Transition>> lts, State state, List <State> tmpListVisited, List <Cluster> tmpClusters) {
        
    	// Get the outgoing transitions of the state
    	List <Transition> transitions = lts.get(state);
    	
    	// If the state is a Faulty State
        if(!state.getTypeN().equals("") && !tmpListVisited.contains(state)) {
        	
        	// List of the first states in some clusters
            List <State> newClusterStates = new ArrayList<State>();
            newClusterStates.add(state);
            
            // Until we don't have any cluster in a cluster
            while (!newClusterStates.isEmpty()) {
                State clusterState = newClusterStates.remove(0);
                
                // Check if we have visited this state (i.e. the first state of a cluster)
                if(!tmpListVisited.contains(clusterState)) {
                    List <Integer> tmpListVisitedInCluster =  new ArrayList<Integer>();
                    String neighType = clusterState.getTypeN();
                    List <State> tmpStates = new ArrayList<State>();
                    
                    // Store the labels in the first state in common label
                    List <String> commonLabels = new ArrayList<String>();
                    List <String> allLabels = new ArrayList<String>();
                    for (Transition stateLabels : lts.get(clusterState)) {
                    	commonLabels.add(stateLabels.getLabel());
        			}
                    
                    // Call the other recursive function
                    traverseCluster(lts, clusterState, clusterState, tmpListVisitedInCluster,
                        tmpListVisited, tmpStates, commonLabels, allLabels, newClusterStates);
                 
                    // Gather the labels from the Faulty States we have found in the cluster
//                    List <String> clusterCommonLabels = new ArrayList<String>();
//                    List <String> clusterAllLabels = new ArrayList<String>();
//                    for (Transition trans : lts.get(clusterState)) {
//                    	clusterCommonLabels.add(trans.getLabel());
//                    	clusterAllLabels.add(trans.getLabel());
//                    }
//                    for (State stateSearchCommonLbl : tmpStates) {
//                        List <String> otherStateLabels = new ArrayList<String>();
//                        for (Transition trans : lts.get(stateSearchCommonLbl)) {
//                            otherStateLabels.add(trans.getLabel());
//                        }
//                        List <String> clusterAllLabelsTmp = new ArrayList<String>(otherStateLabels);
//                        clusterAllLabelsTmp.removeAll(clusterAllLabels);
//                        clusterAllLabels.addAll(clusterAllLabelsTmp);
//                        clusterCommonLabels.retainAll(otherStateLabels);
//                    }
                    
                    // Add the cluster to the list of clusters
                    Cluster tmpCluster = new Cluster(tmpStates.size(), neighType, tmpStates, commonLabels, allLabels);
                    tmpClusters.add(tmpCluster);
                }
            }
            tmpListVisited.add(state);
        } else if(transitions.size() == 0) {
            return;
        } else { // if the state is a normal state (of the blue ones)
            // We have to re-order the transitions such that we always traverse the Faulty States first
            int transOrder = 0;
            int transOrderSwap = 0;
            for (Transition transition : transitions) {
                if(transition.getTarget().getTypeN()!= ""){ // A neighborhood
                    Transition tmpFirstTransition = transitions.get(transOrderSwap);
                    transitions.set(transOrderSwap, transition);
                    transitions.set(transOrder, tmpFirstTransition);
                    transOrderSwap++;
                }
                transOrder++;
            }

            // Traverse to the next state
            for (Transition transition : transitions) {
            	traverseLTS(lts, transition.getTarget(), tmpListVisited, tmpClusters);
            }
        }
    }

    static void printClusters(List<Cluster> tmpClusters) {
        System.out.println("Number of Clusters = " + tmpClusters.size()+"\n");
        for (Cluster cluster : tmpClusters) {
            System.out.println("> Cluster Id = " + cluster.getId());
            System.out.println(">>> Number of Faulty States  = " + cluster.getNumberOfStates());
            System.out.println(">>> Neighborhood type = " + cluster.getNeighType());
            System.out.println(">>> Neighborhoods =>");
            for (State state : cluster.getNeighborhoods()) {
                System.out.println(">>>>> State Id " + state.getId() + ", N = " + state.getTypeN());
            }
            System.out.println(">>> Common Label => ");
            for (String commonlabel : cluster.getCommonLabels()) {
                System.out.println(">>>>> " + commonlabel);
            }
            System.out.println(">>> All Labels =>");
            for (String label : cluster.getAllLabels()) {
                System.out.println(">>>>> " + label);
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        // Check input
        if (args.length < 1) {
            System.out.println("Usage : java newLTS <name>");
            System.exit(0);
        }

        // Init LTS and list of strings
        Map<State,List<Transition>> ltsMap = new HashMap<State, List<Transition>>();
        Map<Integer, String> mapN = new HashMap<Integer, String>();
        String fileName = args[0]+".autx";
        List <String> listring = new ArrayList<String>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.skip(1).forEach(listring::add);
        } catch (Exception ex) {
            System.out.println("Error reading file...!!!");
            System.exit(0);
        }
        State firstState = new State(0, getStateColor(0), "");
        ltsMap.put(firstState, new ArrayList<Transition>());

        // Loop through the list to get map of N
        for (String row : listring) {
            String[] rowArray = row.split(",");
            String[] rowArrayLeft = rowArray[0].split(":");
            if(rowArrayLeft.length > 1) {
                if(rowArrayLeft[1].equals("N")){
                    int tmpId = Integer.parseInt(rowArrayLeft[0].replaceAll("[^\\d-]", ""));
                    String tmpN = rowArrayLeft[2];
                    mapN.put(tmpId, tmpN);
                }
            }     
        }
        // printN(mapN);

        // Loop through the list
        for (String row : listring) {
            String[] rowArray = row.split(",");
            String[] rowArrayMid = rowArray[1].split(":");
            
            // Get the states
            int sourceStateId = Integer.parseInt(rowArray[0].replaceAll("[^\\d-]", ""));
            State sourceState = new State(sourceStateId, getStateColor(sourceStateId), mapN.get(sourceStateId));
            int targetStateId = Integer.parseInt(rowArray[2].replaceAll("[^\\d-]", ""));
            State targetState = new State(targetStateId, getStateColor(targetStateId), mapN.get(targetStateId));
            
            // Build the transition
            Transition newTrans = new Transition(sourceState, 
                        targetState, rowArrayMid[0].replaceAll("\\s+",""), rowArrayMid[1]);
            List<Transition> tmpList = new ArrayList<Transition>();

            // Add the states to Map
            if (!ltsMap.containsKey(sourceState)) {
                ltsMap.put(sourceState, tmpList);
            }
            if (!ltsMap.containsKey(targetState)) {
                ltsMap.put(targetState, tmpList);
            }

            // Add the List
            tmpList = ltsMap.get(sourceState);
            tmpList.add(newTrans);
            ltsMap.put(sourceState, tmpList);
        }

        // printLTS(ltsMap);
        List <State> tmpListVisited = new ArrayList<State>();
        List <Cluster> tmpClusters = new ArrayList<Cluster>();
        traverseLTS(ltsMap, firstState, tmpListVisited, tmpClusters);
        printClusters(tmpClusters);
    }
}