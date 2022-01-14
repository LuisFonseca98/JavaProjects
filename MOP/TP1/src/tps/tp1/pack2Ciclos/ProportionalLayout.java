package tps.tp1.pack2Ciclos;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;

/**
 * Proportional layout - é como um border Layout mas com proporções. Por
 * exemplo: a parte north tem 20% de extensão vertical... Os componentes são
 * então colocados de acordo com a proporcionalidade indicada. Este layout
 * considera os insets do container e pode adicionar seus insets que são
 * proporcionais. Considera hgap e vgap, como pixels or proporcionais
 * 
 * By: António Teófilo, em desenvolvimento
 */

public class ProportionalLayout implements LayoutManager2 {

	public static final String NORTH = "North";
	public static final String SOUTH = "South";
	public static final String EAST = "East";
	public static final String WEST = "West";
	public static final String CENTER = "Center";

	// proportional and pixel oriented GAPS
	int hgap = 0;
	int vgap = 0;
	float hpgap = 0.0f;
	float vpgap = 0.0f;

	boolean isGapsproportional = true;

	Component north = null;
	Component south = null;
	Component east = null;
	Component west = null;
	Component center = null;

	private float insetTop = 0.0f;

	private float insetBottom = 0.0f;

	private float insetLeft = 0.0f;

	private float insetRight = 0.0f;

	private float zonesetTop = 0.0f;

	private float zonesetBottom = 0.0f;

	private float zonesetLeft = 0.0f;

	private float zonesetRight = 0.0f;

	public ProportionalLayout() {
		setZonesets(0.2f);
	}

	public ProportionalLayout(float zonesets) {
		setZonesets(zonesets);
	}

	public ProportionalLayout(float verticalZonesets, float horizontalZonesets) {
		setZonesets(verticalZonesets, horizontalZonesets);
	}

	public ProportionalLayout(float zonesetTop, float zonesetBottom, float zonesetLeft, float zonesetRight) {
		setZonesets(zonesetTop, zonesetBottom, zonesetLeft, zonesetRight);
	}

	public int getHgap() {
		return hgap;
	}

	public void setHgap(int hgap) {
		this.hgap = hgap;
	}

	public int getVgap() {
		return vgap;
	}

	public float getHpgap() {
		return hpgap;
	}

	public void setGaps(int gap) {
		setHgap(gap);
		setVgap(gap);
	}

	public void setHpgap(float hpgap) {
		this.hpgap = hpgap;
	}

	public float getVpgap() {
		return vpgap;
	}

	public void setVpgap(float vpgap) {
		this.vpgap = vpgap;
	}

	public void setVgap(int vgap) {
		this.vgap = vgap;
	}

	public void setPGaps(float gap) {
		setHpgap(gap);
		setVpgap(gap);
	}

	public boolean isGapsproportional() {
		return isGapsproportional;
	}

	public void setGapsproportional(boolean isGapsproportional) {
		this.isGapsproportional = isGapsproportional;
	}

	/**
	 * 
	 * @param insets
	 *            e zonesets Pode variar de 0.0f a 1.0f, e significa o rebordo a
	 *            utilizar. 1.0f é relativo a metade da dimensão da frame.
	 *            ALTERADO PARA 1.0f CORRESPONDE A 100% DA DIMENSÂO DA FRAME
	 */
	public void setInsets(float insets) {
		setInsets(insets, insets, insets, insets);
	}

	public void setInsets(float verticalInsets, float horizontalInsets) {
		setInsets(verticalInsets, verticalInsets, horizontalInsets, horizontalInsets);
	}

	public void setInsets(float insetTop, float insetBottom, float insetLeft, float insetRight) {
		if (insetTop < 0.0f || insetBottom < 0.0f || insetLeft < 0.0f || insetRight < 0.0f)
			throw new IllegalArgumentException("Insets must be a positive float");

		if (insetTop + insetBottom + zonesetBottom + zonesetTop >= 0.8f)
			throw new IllegalArgumentException("Vertical insets plus zonesets must be less than 0.8f (80% of frame");

		if (insetLeft + insetRight + zonesetLeft + zonesetRight >= 0.8f)
			throw new IllegalArgumentException("Horizontal insets plus zonesets must be less than 0.8f (80% of frame");

		this.insetTop = insetTop;
		this.insetBottom = insetBottom;
		this.insetLeft = insetLeft;
		this.insetRight = insetRight;
	}

	public void setZonesets(float zonesets) {
		setZonesets(zonesets, zonesets, zonesets, zonesets);
	}

	public void setZonesets(float verticalZonesets, float horizontalZonesets) {
		setZonesets(verticalZonesets, verticalZonesets, horizontalZonesets, horizontalZonesets);
	}

	public void setZonesets(float zonesetTop, float zonesetBottom, float zonesetLeft, float zonesetRight) {
		if (zonesetTop < 0.0f || zonesetBottom < 0.0f || zonesetLeft < 0.0f || zonesetRight < 0.0f)
			throw new IllegalArgumentException("zoneSets must be a positive float");

		if (insetTop + insetBottom + zonesetBottom + zonesetTop >= 0.8f)
			throw new IllegalArgumentException("Vertical insets plus zonesets must be less than 0.8f (80% of frame");

		if (insetLeft + insetRight + zonesetLeft + zonesetRight >= 0.8f)
			throw new IllegalArgumentException("Horizontal insets plus zonesets must be less than 0.8f (80% of frame");

		this.zonesetTop = zonesetTop;
		this.zonesetBottom = zonesetBottom;
		this.zonesetLeft = zonesetLeft;
		this.zonesetRight = zonesetRight;
	}

	/* Required by LayoutManager. */
	public void addLayoutComponent(String name, Component comp) {
		// System.out.println("PL: called addLayoutComponent with::: " + name
		// + " " + comp);

		if (name == null)
			name = "Center";

		if ("Center".equals(name)) {
			center = comp;
		} else if ("North".equals(name)) {
			north = comp;
		} else if ("South".equals(name)) {
			south = comp;
		} else if ("East".equals(name)) {
			east = comp;
		} else if ("West".equals(name)) {
			west = comp;
		} else {
			throw new IllegalArgumentException("cannot add to layout: unknown constraint: " + name);
		}

	}

	/* Required by LayoutManager. */
	public void addLayoutComponent(Component comp, Object constraints) {
		// System.out.println("PL: called addLayoutComponent with " + comp + " "
		// + constraints);
		if ((constraints == null) || (constraints instanceof String)) {
			addLayoutComponent((String) constraints, comp);
		} else {
			throw new IllegalArgumentException("cannot add to layout: constraint must be a string (or null)");
		}
	}

	/* Required by LayoutManager. */
	public void removeLayoutComponent(Component comp) {
		// System.out.println("PL: removeLayoutComponent called");
		if (comp.equals(center)) {
			center = null;
		} else if (comp.equals(north)) {
			north = null;
		} else if (comp.equals(south)) {
			south = null;
		} else if (comp.equals(east)) {
			east = null;
		} else if (comp.equals(west)) {
			west = null;
		} else
			throw new IllegalArgumentException("cannot remove component: unknown constraint: " + comp);
	}

	public Component getLayoutComponent(Object constraints) {
		// System.out.println("PL: getLayoutComponent called");
		if (CENTER.equals(constraints))
			return center;
		if (NORTH.equals(constraints))
			return north;
		if (SOUTH.equals(constraints))
			return south;
		if (WEST.equals(constraints))
			return west;
		if (EAST.equals(constraints))
			return east;
		throw new IllegalArgumentException("cannot get component: unknown constraint: " + constraints);
	}

	public Component getLayoutComponent(Container target, Object constraints) {
		// System.out
		// .println("PL: Unimplemented method getLayoutComponent has called
		// ------");
		return null;
	}

	/* Required by LayoutManager. */
	public Dimension preferredLayoutSize(Container parent) {
		// System.out.println("PL: preferredLayoutSize called");
		Dimension dim = new Dimension(0, 0);

		// setSizes(parent);
		if (center != null) {
			dim = center.getPreferredSize();
			// System.out.println("prefered size of center component -> " +
			// dim);
			dim.width /= (1 - insetLeft - insetRight - zonesetLeft - zonesetRight);
			dim.height /= (1 - insetTop - insetBottom - zonesetTop - zonesetBottom);
		}

		// Always add the container's insets!
		Insets insets = parent.getInsets();
		dim.width += insets.left + insets.right;
		dim.height += insets.top + insets.bottom;

		return dim;
	}

	/* Required by LayoutManager. */
	public Dimension minimumLayoutSize(Container parent) {
		// System.out.println("PL: minimumLayoutSize called");
		Dimension dim = new Dimension(0, 0);

		// setSizes(parent);
		if (center != null) {
			dim = center.getMinimumSize();
			// System.out.println("minimum size of center component -> " + dim);
			dim.width /= (1 - insetLeft - insetRight - zonesetLeft - zonesetRight);
			dim.height /= (1 - insetTop - insetBottom - zonesetTop - zonesetBottom);
		}

		// Always add the container's insets!
		Insets insets = parent.getInsets();
		dim.width += insets.left + insets.right;
		dim.height += insets.top + insets.bottom;

		return dim;
	}

	/* Required by LayoutManager. */
	/*
	 * This is called when the panel is first displayed, and every time its size
	 * changes. Note: You CAN'T assume preferredLayoutSize or minimumLayoutSize
	 * will be called -- in the case of applets, at least, they probably won't
	 * be.
	 */
	public void layoutContainer(Container parent) {
		// System.out.println("PL: layoutContainer(Container parent) called");
		Insets insets = parent.getInsets();

		int x = 0, width = 0, y = 0, height = 0;
		int parentWidth = parent.getWidth() - insets.left - insets.right;
		int parentHeight = parent.getHeight() - insets.top - insets.bottom;

		int hgap = isGapsproportional ? (int) (hpgap * parentWidth) : this.hgap;
		int vgap = isGapsproportional ? (int) (vpgap * parentHeight) : this.vgap;

		int x1 = (int) (parentWidth * insetLeft);
		int x2 = (int) (parentWidth * (insetLeft + zonesetLeft));
		int x2inner = x2 + hgap;
		int x3 = (int) (parentWidth * (1 - insetRight - zonesetRight));
		int x3inner = x3 - hgap;
		int x4 = (int) (parentWidth * (1 - insetRight));

		int y1 = (int) (parentHeight * insetTop);
		int y2 = (int) (parentHeight * (insetTop + zonesetTop));
		int y2inner = y2 + vgap;
		int y3 = (int) (parentHeight * (1 - insetBottom - zonesetBottom));
		int y3inner = y3 - vgap;
		int y4 = (int) (parentHeight * (1 - insetBottom));

		if (west == null || !west.isVisible() || zonesetLeft == 0.0f)
			x2inner = x2;
		if (east == null || !east.isVisible() || zonesetRight == 0.0f)
			x3inner = x3;
		if (north == null || !north.isVisible() || zonesetTop == 0.0f)
			y2inner = y2;
		if (south == null || !south.isVisible() || zonesetBottom == 0.0f)
			y3inner = y3;

		// Center component
		if (center != null && center.isVisible()) {
			Component c = center;
			x = x2inner;
			y = y2inner;
			width = x3inner - x2inner;
			height = y3inner - y2inner;
			// Set the component's size and position.
			c.setBounds(x + insets.left, y + insets.top, width, height);
		}

		// Top component
		if (north != null && north.isVisible()) {
			Component c = north;
			x = x1;
			y = y1;
			width = x4 - x1;
			height = y2 - y1;
			// Set the component's size and position.
			c.setBounds(x + insets.left, y + insets.top, width, height);
		}

		// South component
		if (south != null && south.isVisible()) {
			Component c = south;
			x = x1;
			y = y3;
			width = x4 - x1;
			height = y4 - y3;
			// Set the component's size and position.
			c.setBounds(x + insets.left, y + insets.top, width, height);
		}

		// Left component
		if (west != null && west.isVisible()) {
			Component c = west;
			x = x1;
			y = y2inner;
			width = x2 - x1;
			height = y3inner - y2inner;
			// Set the component's size and position.
			c.setBounds(x + insets.left, y + insets.top, width, height);
		}

		// Rigth component
		if (east != null && east.isVisible()) {
			Component c = east;
			x = x3;
			y = y2inner;
			width = x4 - x3;
			height = y3inner - y2inner;
			// Set the component's size and position.
			c.setBounds(x + insets.left, y + insets.top, width, height);
		}
	}

	public String toString() {
		// System.out.println("PL: toString called");
		return getClass().getName();
	}

	public void repaint(Container parent) {
		// System.out.println("PL: repaint called");
		// setSizes(parent);
	}

	public Dimension maximumLayoutSize(Container target) {
		// System.out.println("PL: maximumLayoutSize called");
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	public float getLayoutAlignmentX(Container target) {
		// System.out.println("PL: getLayoutAlignmentX called");
		return 0.5f;
	}

	public float getLayoutAlignmentY(Container target) {
		// System.out.println("PL: getLayoutAlignmentY called");
		return 0.5f;
	}

	public void invalidateLayout(Container target) {
		// System.out.println("PL: invalidateLayout called");
	}
}
