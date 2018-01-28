package NHSensor.NHSensorSim.ui.mainFrame;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.swing.JPanel;

import NHSensor.NHSensorSim.algorithm.AlgorithmProperty;
import NHSensor.NHSensorSim.experiment.ExperimentParam;

import com.l2fprod.common.propertysheet.DefaultProperty;
import com.l2fprod.common.propertysheet.Property;
import com.l2fprod.common.propertysheet.PropertySheet;
import com.l2fprod.common.propertysheet.PropertySheetPanel;
import com.l2fprod.common.swing.LookAndFeelTweaks;

public class AlgorithmParamPage extends JPanel {
	public static final Class THIS_CLASS = AlgorithmParamPage.class;
	private AlgorithmProperty algorithmProperty;
	private ExperimentParam param;
	Class algParamClass;
	Field fields[];
	Method[] setMethods;
	String[] fieldNames;
	Property[] properties;

	public AlgorithmParamPage(final AlgorithmProperty algorithmProperty) throws Exception {
		setLayout(LookAndFeelTweaks.createVerticalPercentLayout());
		this.algorithmProperty = algorithmProperty;
		algParamClass = Class.forName(algorithmProperty.getExperimentParamClassFullName());
		param = (ExperimentParam) algParamClass.newInstance();
		fields = algParamClass.getDeclaredFields();
		setMethods = new Method[fields.length];
		fieldNames = new String[fields.length];
		properties = new Property[fields.length];
		String fieldName, firstLetter, setMethodName;
		Field field;
		
		for (int i = 0; i < fields.length; i++) {
			field = fields[i];
			fieldName = field.getName();
			firstLetter = fieldName.substring(0, 1).toUpperCase();
			setMethodName = "set" + firstLetter + fieldName.substring(1);
			try {
				setMethods[i] = algParamClass.getMethod(setMethodName,
						new Class[] { field.getType() });
				fieldNames[i] = fieldName;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			} 
		}

		final PropertySheetPanel sheet = new PropertySheetPanel();
		sheet.setMode(PropertySheet.VIEW_AS_FLAT_LIST);
		
		for(int i=0;i<fieldNames.length;i++) {
			if(fieldNames[i]!=null) 
				properties[i] = new NodeComponentProperty(i);
		}
		sheet.setProperties(properties);
		sheet.readFromObject(param);
		sheet.setDescriptionVisible(true);
		sheet.setSortingCategories(true);
		sheet.setSortingProperties(true);
		add(sheet, "*");

		// everytime a property change, update the button with it
		PropertyChangeListener listener = new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				Property prop = (Property) evt.getSource();
				prop.writeToObject(param);
				
				System.out.println("Updated object to " + param);
			}
		};
		sheet.addPropertySheetChangeListener(listener);

	}
	
	public class NodeProperty extends DefaultProperty {
		public NodeProperty() {
			setName("id");
			setCategory("Node.cat");
			setDisplayName("节点");
			setShortDescription("节点");
			setType(Integer.class);

			for(int i=0;i<fieldNames.length;i++) {
				if(fieldNames[i]!=null) 
					addSubProperty(new NodeComponentProperty(i));
			}
		}
	}

	public class NodeComponentProperty extends DefaultProperty {
		public NodeComponentProperty(int i) {
			String name = fieldNames[i];
			setName(name);
			setDisplayName(name);
			setShortDescription("");
			setType(fields[i].getType());
		}
	}
}
