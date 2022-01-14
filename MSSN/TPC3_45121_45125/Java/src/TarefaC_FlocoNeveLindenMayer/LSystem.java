package TarefaC_FlocoNeveLindenMayer;

public class LSystem {

	private String sequencia;
	private int generation;
	private Regra[] ruleset;

	public LSystem(String axioma,Regra[] regras) {

		this.sequencia = axioma;
		this.ruleset = regras;
		this.generation = 0;
	}

	public void setRuleset(Regra[] ruleset) {
		this.ruleset = ruleset;
	}

	public String getSequence() {
		return sequencia;
	}

	public int getGeneration() {
		return generation;
	}
	
	public Regra[] Regra50(Regra a, Regra b) {
		double i =  Math.random();
		
		if (i >= 0.5) 
			return new Regra[] {a} ;
		
		return new Regra[] {b};
	}
	
	public void nextGeneration() {
		String nextGen = "";
		generation ++ ;
		for(int i = 0; i < sequencia.length();i++) {
			char c = sequencia.charAt(i); //percorrer o caminho da geracao seguinte
			String replace = "" + c;
			for(int j = 0; j < ruleset.length;j++) {
				if(c == ruleset[j].getSymbol()) {//verifica se encontrou a regra
					replace = ruleset[j].getString();
					break;
				}
			}
			nextGen += replace;

		}
		sequencia = nextGen;
	}

}
