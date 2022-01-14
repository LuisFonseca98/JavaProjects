package plantraj.modProb;

public abstract class Estado {
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Estado){
			if(hashCode() == obj.hashCode()){
				return true;
			}
		}
		return false;
	}
	
	//inteiro unico gerado a partir do objecto
	public abstract int hashCode();

}
