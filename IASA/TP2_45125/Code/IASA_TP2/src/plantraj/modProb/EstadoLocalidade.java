package plantraj.modProb;

public class EstadoLocalidade extends Estado{
	
	private String localidade;
	
	public EstadoLocalidade(String localidade){
		this.localidade = localidade;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return localidade.hashCode();
	}
	
	//override no metodo inbuilt da classe abstracta estado
	@Override
	public String toString(){
		return "Estado: " + localidade;
	}

}
