package ca;
import processing.core.PApplet;

public class MajorityCA extends CellularAutomata
{
	public MajorityCA(PApplet p, int nrows, int ncols, int radius, 
			boolean moore, int numberOfStates)
	{
		super(p, nrows, ncols, radius, moore, numberOfStates);
	}

	@Override
	protected void createGrid()
	{
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				cells[i][j] = new MajorityCell(this, i, j);
			}
		}
		if (moore) setNeighbors();
		else setNeighbors4();
	}

	public boolean majorityRule() {
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				((MajorityCell) cells[i][j]).computeHistogram();
			}
		}

		boolean anyChanged = false;
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				boolean changed = 
						((MajorityCell) cells[i][j]).applyMajorityRule();
				if (changed) anyChanged = true;
			}
		}
		return anyChanged;
	}
}
